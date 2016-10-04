package view;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import annotation.RowElement;
import annotation.SessionForm;
import components.LabelCheckBox;
import components.LabelComboBox;
import components.LabelComponent;
import components.LabelRadioButton;
import components.LabelSpinner;
import components.LabelTextField;
import genericObject.GenericClass;
import genericObject.GenericField;
import layout.HorizontalLayout;
import layout.HorizontalLayoutConstraint;

@SuppressWarnings("serial")
public class SessionFormPanel extends JPanel {

	private ArrayList<ArrayList<GenericField>> rowFields;
	private Class<?> typeClass;
	private GenericClass gClass;

	protected SessionFormPanel(int gap, Class<?> typeClss) {
		super(new HorizontalLayout(gap));

		rowFields = new ArrayList<>();
		typeClass = typeClss;
		gClass = new GenericClass(RowElement.class, typeClass);

		generateBorder(typeClass.getAnnotation(SessionForm.class));
		ordenateRows(gClass);
		createSessionForm(typeClass);
	}

	public Class<?> getTypeClass() {
		return typeClass;
	}

	public ArrayList<ArrayList<GenericField>> getRowFields() {
		return rowFields;
	}

	private void ordenateRows(GenericClass gClass) {

		for (GenericField gField : gClass.getGenericFields()) {

			RowElement element = (RowElement) gField.getAnnotation();

			if (element.index() < 0) {
				throw new IllegalArgumentException(gField + " RowElement index must be >= 0.");
			}

			if (element.index() == rowFields.size()) {

				rowFields.add(new ArrayList<>());

			} else if (element.index() > rowFields.size()) {

				for (int i = rowFields.size(); i <= element.index(); i++) {
					rowFields.add(new ArrayList<>());
				}
			}
			rowFields.get(element.index()).add(gField);
		}
	}

	private void createSessionForm(Class<?> clss) {

		for (ArrayList<GenericField> fields : rowFields) {

			if (fields.size() == 0) {
				continue;
			}

			for (GenericField field : fields) {

				HorizontalLayoutConstraint constraint = new HorizontalLayoutConstraint(field);
				add(getComponent(field, constraint), constraint);
			}
		}
	}

	private void generateBorder(Annotation annotation) {
		if (annotation != null) {
			setBorder(BorderFactory.createTitledBorder(((SessionForm) annotation).title()));
		}
	}

	private LabelComponent getComponent(GenericField gField, HorizontalLayoutConstraint c) {

		if (c.getTypeClass().equals("") || c.getTypeClass().equals(JTextField.class.getSimpleName())) {

			return new LabelTextField(gField, c.getMinWidth(), c.getTitle(), c.getValues());

		} else if (c.getTypeClass().equals(JComboBox.class.getSimpleName())) {

			return new LabelComboBox(gField, c.getMinWidth(), c.getTitle(), c.getValues());

		} else if (c.getTypeClass().equals(JCheckBox.class.getSimpleName())) {

			return new LabelCheckBox(gField, c.getMinWidth(), c.getTitle(), c.getValues());

		} else if (c.getTypeClass().equals(JRadioButton.class.getSimpleName())) {

			return new LabelRadioButton(gField, c.getMinWidth(), c.getTitle(), c.getValues());

		} else if (c.getTypeClass().equals(JSpinner.class.getSimpleName())) {

			return new LabelSpinner(gField, c.getMinWidth(), c.getTitle());

		} else {
			throw new IllegalArgumentException(
					"<" + c.getTypeClass() + ">" + " is not a type class expected. \nUse JTextField (default), "
							+ "JComboBox, JCheckBox, JRadioButton or JSpinner class.");
		}
	}
}
