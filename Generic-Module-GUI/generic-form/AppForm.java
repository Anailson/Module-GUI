import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class AppForm {

	@Teste(valores = { "ValoresEnum.UM", "ValoresEnum.DOIS" })
	private int i;

	// String = JTextField || JComboBox || JCheckBox || JRadioButton;
	// Integer = JTextField || JComboBox || JCheckBox || JRadioButton ||
	// 			JSpinner;
	// Boolean = JRadioButton

	public AppForm() {
		Field field = this.getClass().getDeclaredFields()[0];
		Teste teste = (Teste) field.getAnnotation(Teste.class);

		for (String s : teste.valores()) {
			System.out.println("<" + s + ">");
		}
	}

	public static void main(String[] args) {
		new AppForm();
	}
}
/**
 * 
 * @author anail
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Teste {

	/**
	 * 
	 * @return
	 */
	String[] valores();
}

enum ValoresEnum {
	UM, DOIS;
}