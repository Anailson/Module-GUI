package form;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import annotation.RowElement;
import components.LabelComboBox;
import components.LabelRadioButton;
import components.LabelTextField;
import genericObject.GenericField;
import genericObject.GenericObject;
import layout.HorizontalLayout;
import layout.HorizontalLayoutConstraint;

@SuppressWarnings("serial")
public class SemiForm extends JPanel {

	private int gap;
	private ArrayList<ArrayList<GenericField>> listFields;

	public SemiForm(int gap, Class<?>... clss) {
		super(new HorizontalLayout(gap));

		this.gap = gap;
		this.listFields = new ArrayList<>();

		for (Class<?> c : clss) {

			generateRowsForm(new GenericObject(RowElement.class, c));
		}
	}

	private void generateRowsForm(GenericObject gObject) {

		for (GenericField gField : gObject.getGenericFields()) {

			ordenateRows(gField);
		}

		for (ArrayList<GenericField> fields : listFields) {

			if (fields.size() == 0) {
				continue;
			}

			for (GenericField field : fields) {

				RowElement element = (RowElement) field.getAnnotation();
				String title = element.title();

				if (title.equals("")) {
					title = field.getFieldName();
				}
				add(getJComponent(title, element), new HorizontalLayoutConstraint(element.index(), element.required(),
						element.resizable()));
			}
		}
	}

	private void ordenateRows(GenericField gField) {

		RowElement elem = (RowElement) gField.getAnnotation();
		int row = elem.index();

		if (row < 0) {
			throw new IllegalArgumentException(gField + " RowElement index must be >= 0.");
		}

		if (row == listFields.size()) {

			listFields.add(new ArrayList<>());

		} else if (row > listFields.size()) {

			for (int i = listFields.size(); i <= row; i++) {
				listFields.add(new ArrayList<>());
			}
		}
		listFields.get(row).add(gField);
	}

	private JComponent getJComponent(String label, RowElement element) {

		String typeClass = element.typeClass();

		if (typeClass.equals("") || typeClass.equalsIgnoreCase(JTextField.class.getSimpleName())) {

			return new LabelTextField(gap, label, element.values());
		} else if (typeClass.equalsIgnoreCase(JComboBox.class.getSimpleName())) {

			return new LabelComboBox(gap, label, element.values()); 

		} else if (typeClass.equalsIgnoreCase(JCheckBox.class.getSimpleName())) {

			return new JCheckBox(label);

		} else if (typeClass.equalsIgnoreCase(JRadioButton.class.getSimpleName())) {

			return new LabelRadioButton(gap, label, element.values());

		} else if (typeClass.equalsIgnoreCase(JSpinner.class.getSimpleName())) {

			return new JSpinner(); //TODO LabelSpinner 
			
		} else {
			throw new IllegalArgumentException(
					element.typeClass() + " is not a type class expected. \nUse JTextField (default), "
							+ "JComboBox, JCheckBox, JRadioButton or JSpinner class.");
		}
	}
}
