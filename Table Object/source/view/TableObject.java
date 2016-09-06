package view;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TableObject {

	private static final String OK = "OK";

	private ArrayList<FieldObject> fieldObjects;
	private Class<?> childClass;

	public TableObject() {

		fieldObjects = new ArrayList<>();
		childClass = getClass();
		Field[] fields = childClass.getDeclaredFields();

		for (Field field : fields) {

			FieldObject fieldObject = new FieldObject(childClass, field);
			String check = verifyGetterAndSetter(fieldObject);

			if (check == OK) {
				fieldObjects.add(fieldObject);
			} else {
				throw new IllegalStateException(check);
			}
		}
	}

	public Class<?> getChildClass() {
		return childClass;
	}
	
	protected Class<?> getFieldType(String field){
		
		return getField(field).getExpectedType();
	}

	protected String getGetterName(String field) {
		return getField(field).getGetterName();
	}

	protected String getSetterName(String field) {
		return getField(field).getSetterName();
	}

	private String verifyGetterAndSetter(FieldObject field) {

		boolean flag = false;
		String error = field.getExpectedType().getSimpleName() + " " + field.getName()
				+ ". You must declare default method.\nExpected: ";

		if (!field.hasGetter()) {
			flag = true;
			error += field.getExpectedType() + " " + field.getSignGetter() + "; ";
		}

		if (!field.hasSetter()) {
			flag = true;
			error += "void " + field.getSignSetter() + "; ";
		}

		if (flag) {
			return error;
		}
		return OK;
	}
	
	private FieldObject getField(String field){
		for(FieldObject fieldObject : fieldObjects){
		
			if (fieldObject.getName().equals(field)){
				return fieldObject;
			}
		}
		return null;
	}
}
