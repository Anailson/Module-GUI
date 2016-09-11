package view;

import java.security.InvalidParameterException;

public class Column {
	
	public final static int MIN_WIDTH = 0;
	private String title;
	private String field;
	private int width;
	private boolean editable;
	private Class<?> type;

	// TODO Refator and remove the field Class<?> cls. It's possible using
	// FieldObject?

	protected Column(String field, String title, int width, boolean editable, Class<?> cls) {
		
		if(field == null || cls == null){
			throw new InvalidParameterException("Parameters field & cls cannot be null. They must match with fields of your object class.");
		}
		if(width < MIN_WIDTH){
			throw new InvalidParameterException("Width of column '" + field + "' must be >= 0.");
		}
		
		this.field = field;
		this.title = title;
		this.width = width;
		this.type = cls;
		this.editable = editable;
	}

	protected String getTitle() {
		return title.toUpperCase();
	}

	protected String getField() {
		return field;
	}
	
	protected int getWidth() {
		return width;
	}

	protected Class<?> getClassType() {

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

	protected Class<?> getDefaultType() {

		return type;
	}

	protected boolean isEditable() {
		return editable;
	}

	@Override
	public String toString() {
		return field + " [Class=" + type.getName() + ", Title=" + title + ", Editable=" + editable + "]";
	}
}
