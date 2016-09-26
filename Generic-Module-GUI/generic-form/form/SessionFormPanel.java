package form;

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
import annotation.RowElementSettings;
import annotation.SessionForm;
import components.LabelCheckBox;
import components.LabelComboBox;
import components.LabelComponent;
import components.LabelRadioButton;
import components.LabelSpinner;
import components.LabelTextField;
import genericObject.GenericField;
import genericObject.GenericObject;
import layout.HorizontalLayout;
import layout.HorizontalLayoutConstraint;

@SuppressWarnings("serial")
public class SessionFormPanel extends JPanel {

	private ArrayList<ArrayList<GenericField>> listFields;

	protected SessionFormPanel(int gap, Class<?> clss) {
		super(new HorizontalLayout(gap));

		listFields = new ArrayList<>();

		ordenateRows(new GenericObject(RowElement.class, clss));
		createSessionForm(clss);
	}

	private void ordenateRows(GenericObject gObject) {

		for (GenericField gField : gObject.getGenericFields()) {

			RowElement elem = (RowElement) gField.getAnnotation();

			if (elem.index() < 0) {
				throw new IllegalArgumentException(gField + " RowElement index must be >= 0.");
			}

			if (elem.index() == listFields.size()) {

				listFields.add(new ArrayList<>());

			} else if (elem.index() > listFields.size()) {

				for (int i = listFields.size(); i <= elem.index(); i++) {
					listFields.add(new ArrayList<>());
				}
			}
			listFields.get(elem.index()).add(gField);
		}
	}

	private void createSessionForm(Class<?> clss) {

		generateBorder(clss.getAnnotation(SessionForm.class));

		for (ArrayList<GenericField> fields : listFields) {

			if (fields.size() == 0) {
				continue;
			}

			for (GenericField field : fields) {

				RowElement element = (RowElement) field.getAnnotation();
				add(getJComponent(field), new HorizontalLayoutConstraint(element));
			}
		}
	}

	private void generateBorder(Annotation annotation) {
		if (annotation != null) {
			setBorder(BorderFactory.createTitledBorder(((SessionForm) annotation).title()));
		}
	}

	private LabelComponent getJComponent(GenericField field) {

		RowElementSettings set = getSettings(field);

		if (set.typeClass().equals("") || set.typeClass().equals(JTextField.class.getSimpleName())) {

			return new LabelTextField(set.minWidth(), set.title(), set.values());

		} else if (set.typeClass().equals(JComboBox.class.getSimpleName())) {

			return new LabelComboBox(set.minWidth(), set.title(), set.values());

		} else if (set.typeClass().equals(JCheckBox.class.getSimpleName())) {

			return new LabelCheckBox(set.minWidth(), set.title(), set.values());

		} else if (set.typeClass().equals(JRadioButton.class.getSimpleName())) {

			return new LabelRadioButton(set.minWidth(), set.title(), set.values());

		} else if (set.typeClass().equals(JSpinner.class.getSimpleName())) {

			return new LabelSpinner(set.minWidth(), set.title());

		} else {
			throw new IllegalArgumentException(
					"<" + set.typeClass() + ">" + " is not a type class expected. \nUse JTextField (default), "
							+ "JComboBox, JCheckBox, JRadioButton or JSpinner class.");
		}
	}

	private RowElementSettings getSettings(GenericField field) {

		RowElementSettings settings = field.getField().getAnnotation(RowElementSettings.class);

		if (settings == null) {
			settings = new RowElementSettings() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return RowElementSettings.class;
				}

				@Override
				public int minWidth() {
					return 0;
				}

				@Override
				public String title() {
					return field.getFieldName();
				}

				@Override
				public String typeClass() {
					return "";
				}

				@Override
				public String[] values() {
					return new String[] {};
				}
			};
		}
		return settings;
	}
}
