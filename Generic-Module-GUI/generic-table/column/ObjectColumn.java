package column;

import java.security.InvalidParameterException;

public class ObjectColumn {
	
	public final static int MIN_WIDTH = 0;
	private String title;
	private String field;
	private int width;
	private boolean editable;
	private Class<?> type;

	// TODO Refator and remove the field Class<?> cls. It's possible using
	// FieldObject?

	public ObjectColumn(String field, String title, int width, boolean editable, Class<?> cls) {
		
		if(width < MIN_WIDTH){
			throw new InvalidParameterException("Width of column '" + field + "' must be >= 0.");
		}
		
		if(title.equals("")){
			this.title = field;
		} else {
			this.title = title;
		}
		this.field = field;
		this.width = width;
		this.type = cls;
		this.editable = editable;
	}

	public String getTitle() {
		return title.toUpperCase();
	}
	
	public String getField() {
		return field;
	}
	
	public int getWidth() {
		return width;
	}

	public Class<?> getClassType() {

		if (type.isAssignableFrom(int.class)) {
			return Integer.class;
		} else if (type.isAssignableFrom(float.class)) {
			return Float.class;
		} else if (type.isAssignableFrom(double.class)) {
			return Double.class;
		} else if (type.isAssignableFrom(short.class)) {
			return Short.class;
		} else if (type.isAssignableFrom(long.class)) {
			return Long.class;
		} else if (type.isAssignableFrom(boolean.class)) {
			return Boolean.class;
		} else if (type.isAssignableFrom(char.class)) {
			return String.class;
		}
		return type;
	}

	public Class<?> getDefaultType() {

		return type;
	}

	public boolean isEditable() {
		return editable;
	}

	@Override
	public String toString() {
		return field + " [Class=" + type.getName() + ", Title=" + title + ", Editable=" + editable + "]";
	}
}