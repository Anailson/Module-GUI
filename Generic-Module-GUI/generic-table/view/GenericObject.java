package view;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GenericObject {

	private ArrayList<GenericField> fieldObjects;
	private Class<?> childClass;
	
	protected GenericObject(Class<?> childClass) {

		this.childClass = childClass;
		this.fieldObjects = new ArrayList<>();

		for (Field field : childClass.getDeclaredFields()) {

			GenericField genericField = new GenericField(childClass, field);

			if (genericField.hasColumn()) {

				fieldObjects.add(genericField);
			}
		}
	}

	protected Class<?> getChildClass() {
		return childClass;
	}

	protected Column[] getColumns() {
		Column[] columns = new Column[fieldObjects.size()];

		for (int i = 0; i < fieldObjects.size(); i++) {
			GenericField obj = fieldObjects.get(i);
			columns[i] = new Column(obj.getName(), obj.getTitle(), obj.getWidth(), obj.isEditable(),
					obj.getFieldType());
		}
		return columns;
	}

	protected Class<?> getFieldType(String field) {

		return getField(field).getFieldType();
	}

	protected String getGetterName(String field) {
		return getField(field).getGetterName();
	}

	protected String getSetterName(String field) {
		return getField(field).getSetterName();
	}

	private GenericField getField(String field) {
		for (GenericField fieldObject : fieldObjects) {

			if (fieldObject.getName().equals(field)) {
				return fieldObject;
			}
		}
		return null;
	}
}
