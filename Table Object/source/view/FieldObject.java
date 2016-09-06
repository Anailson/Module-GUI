package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldObject {

	private static final String GET = "get";
	private static final String SET = "set";
	private static final String IS = "is";

	private Field field;
	private Method getter, setter;
	private String partialName;

	protected FieldObject(Class<?> childClass, Field field) {

		this.field = field;
		this.getter = this.setter = null;
		this.partialName = partialName();

		verifyDefaultConstrutor(childClass);
		veirfyDefaultMethods(childClass);
	}

	protected String getName() {
		return field.getName();
	}

	protected String getGetterName() {
		String sign = GET;
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			sign = IS;
		}

		return sign + partialName;
	}

	protected String getSetterName() {
		return SET + partialName;
	}

	protected String getSignGetter() {

		return getGetterName() + "()";
	}

	protected String getSignSetter() {
		return getSetterName() + "(" + getExpectedType().getSimpleName() + ")";
	}

	protected Class<?> getExpectedType() {
		return field.getType();
	}

	protected boolean hasGetter() {
		if (getter != null) {
			return true;
		}
		return false;
	}

	protected boolean hasSetter() {
		if (setter != null) {
			return true;
		}
		return false;
	}

	private void verifyDefaultConstrutor(Class<?> childClass) {

		Constructor<?>[] construtors = childClass.getDeclaredConstructors();
		if (construtors.length > 1) {

			for (Constructor<?> constructor : construtors) {
				if (constructor.getParameterCount() == 0) {
					return;
				}
			}
			throw new IllegalStateException(
					"You must declare a default construtor on " + childClass.getName() + " class.");

		} else if (construtors.length == 1) {

			if (construtors[0].getParameterCount() > 0) {

				throw new IllegalStateException(
						"You must declare a default construtor on " + childClass.getName() + " class.");
			}
		}
	}

	private void veirfyDefaultMethods(Class<?> childClass) {

		Method[] methods = childClass.getDeclaredMethods();

		if (methods.length == 0) {

			throw new IllegalStateException("No method was declared on " + childClass.getName() + " class.\n"
					+ "You must create getters and setters for all fields.");
		}

		// TODO Quais tipos de dados passam aqui.
		verifyGetMethod(methods);
		verifySetMethod(methods);
	}

	private void verifyGetMethod(Method[] methods) {

		String expectedName = GET;

		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			expectedName = IS;
		}

		expectedName += partialName;
		for (Method method : methods) {

			if (method.getName().equals(expectedName)) {
				if (getExpectedType() == method.getReturnType()) {
					getter = method;
					return;
				}
			}
		}
	}

	private void verifySetMethod(Method[] methods) {

		String expectedName = SET + partialName;
		for (Method method : methods) {

			if (method.getName().equals(expectedName)) {

				if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == getExpectedType() && method.getReturnType() == void.class) {

					setter = method;
					return;
				}
			}
		}
	}

	private String partialName() {

		String name = field.getName();
		String oldChar = name.substring(0, 1);
		String newChar = oldChar.toUpperCase();
		return name.replaceFirst(oldChar, newChar);
	}
}