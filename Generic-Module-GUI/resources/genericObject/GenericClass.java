package genericObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GenericClass {

	private ArrayList<GenericField> fields;
	private Class<?> childClass;

	public GenericClass(Class<? extends Annotation> annotation, Class<?> childClass) {

		this.childClass = childClass;
		this.fields = new ArrayList<>();

		for (Field field : childClass.getDeclaredFields()) {

			GenericField genericField = new GenericField(childClass, annotation, field);

			if (genericField.hasColumn()) {

				fields.add(genericField);
			}
		}
	}

	public Class<?> getChildClass() {
		return childClass;
	}

	public ArrayList<GenericField> getGenericFields() {
		return fields;
	}

	public Class<?> getFieldType(String field) {

		return getField(field).getFieldType();
	}

	public String getGetterName(String field) {
		return getField(field).getGetterName();
	}

	public String getSetterName(String field) {
		return getField(field).getSetterName();
	}

	public GenericField getField(String field) {
		for (GenericField fieldObject : fields) {

			if (fieldObject.getFieldName().equals(field)) {
				return fieldObject;
			}
		}
		return null;
	}
}