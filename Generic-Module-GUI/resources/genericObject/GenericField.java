package genericObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GenericField {

	private static final String GET = "get";
	private static final String SET = "set";
	private static final String IS = "is";

	private Class<?> childClass;
	private Field field;
	private Method getter, setter;
	private Annotation annotationType;
	private String partialName;

	protected GenericField(Class<?> childClass, Class<? extends Annotation> annotation, Field field) {

		this.childClass = childClass;
		this.field = field;
		this.getter = this.setter = null;
		this.annotationType = null;
		this.partialName = partialName();

		if (hasAnnotation(annotation)) {

			verifyDefaultConstrutor();
			veirfyDefaultMethods();
		}
	}

	public Field getField(){
		return field;
	}
	
	public String getFieldName() {
		return field.getName();
	}

	public Class<?> getFieldType() {
		return field.getType();
	}

	public Annotation getAnnotation() {
		return this.annotationType;
	}

	public String getGetterName() {
		String sign = GET;
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			sign = IS;
		}

		return sign + partialName;
	}

	public String getSetterName() {
		return SET + partialName;
	}

	protected String getSignGetter() {

		return getGetterName() + "()";
	}

	protected String getSignSetter() {
		return getSetterName() + "(" + getFieldType().getSimpleName() + ")";
	}

	protected boolean hasColumn() {
		if (annotationType != null) {
			return true;
		}
		return false;
	}

	private boolean hasGetter() {
		if (getter != null) {
			return true;
		}
		return false;
	}

	private boolean hasSetter() {
		if (setter != null) {
			return true;
		}
		return false;
	}

	private boolean hasAnnotation(Class<? extends Annotation> annotation) {

		if (field.isAnnotationPresent(annotation)) {

			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType() == annotation) {
					this.annotationType = a;
					return true;
				}
			}
		}
		return false;
	}

	private void verifyDefaultConstrutor() {

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

	private void veirfyDefaultMethods() {

		Method[] methods = childClass.getDeclaredMethods();

		if (methods.length == 0) {

			throw new IllegalStateException("No method was declared on " + childClass.getName() + " class.\n"
					+ "You must create getters and setters for all fields.");
		}

		// TODO Quais tipos de dados passam aqui.
		verifyGetMethod(methods);
		verifySetMethod(methods);

		boolean flag = false;
		String error = getFieldType().getSimpleName() + " " + field.getName()
				+ ". You must declare default method.\nExpected: ";

		if (!hasGetter()) {
			flag = true;
			error += getFieldType() + " " + getSignGetter() + "; ";
		}

		if (!hasSetter()) {
			flag = true;
			error += "void " + getSignSetter() + "; ";
		}

		if (flag) {
			throw new IllegalStateException(error);
		}
	}

	private void verifyGetMethod(Method[] methods) {

		String expectedName = GET;

		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			expectedName = IS;
		}

		expectedName += partialName;
		for (Method method : methods) {

			if (method.getName().equals(expectedName)) {
				if (getFieldType() == method.getReturnType()) {
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

				if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == getFieldType()
						&& method.getReturnType() == void.class) {

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

	@Override
	public String toString() {
		return "[Class=" + childClass.getCanonicalName() + ", Type=" + field.getType().getName() + " Field="
				+ field.getName() + "]";
	}
}