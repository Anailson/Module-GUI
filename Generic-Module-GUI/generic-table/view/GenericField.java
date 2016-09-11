package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import interfaces.Column;

public class GenericField {
	
	private static final String GET = "get";
	private static final String SET = "set";
	private static final String IS = "is";

	private Field field;
	private Method getter, setter;
	private Column column;
	private String partialName;

	protected GenericField(Class<?> childClass, Field field) {

		this.field = field;
		this.getter = this.setter = null;
		this.column = null;
		this.partialName = partialName();

		if (hasAnnotationColumn()) {

			verifyDefaultConstrutor(childClass);
			veirfyDefaultMethods(childClass);
		}
	}

	protected String getName() {
		return field.getName();
	}
	
	protected String getTitle(){

		if(column.title().equals("")){
			return getName();
		}
		return column.title();
	}
	
	protected int getWidth(){
		
		return column.width();
	}
	
	protected boolean isEditable(){
		return column.editable();
	}

	protected Class<?> getFieldType() {
		return field.getType();
	}
	
	protected Column getColumn(){
		return this.column;
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
		return getSetterName() + "(" + getFieldType().getSimpleName() + ")";
	}

	protected boolean hasColumn() {
		if (column != null) {
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

	private boolean hasAnnotationColumn() {
		
		if (field.isAnnotationPresent(Column.class)) {
			column = field.getAnnotation(Column.class);
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
}