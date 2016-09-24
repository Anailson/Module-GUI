package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param
 * 
 * 			int
 *            row: Indicates the row that component will be displayed.
 * 
 *            String settings: Indicates how components will be displayed. All
 *            settings are optional. To change settings, use the syntax
 *            <key> <=> <value> <;>. Keys may be:
 * 
 *            String title: change the label referents of component. For
 *            default, will be displayed field's name.
 * 
 *            boolean required: define if the field is required.
 * 
 *            boolean resizable: define if component will be resizable.
 * 
 *            String class: component's type. May be JTextField (default),
 *            JComboBox, JCheckBox, JRadioButton, JSpinner;
 * 
 *            String values: sets all possible values that will be displayed.
 *            Note: for JTextField class, only the first value will displayed
 *            how hint..
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowElement {

	int index();

	String title();

	boolean required();

	boolean resizable();

	String typeClass();

	String[] values();
}