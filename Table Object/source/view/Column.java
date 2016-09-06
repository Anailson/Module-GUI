package view;

import java.security.InvalidParameterException;

public class Column {
	
	protected final static int MIN_WIDTH = 1;
	private String title;
	private String field;
	private int width;
	private boolean editable;
	private Class<?> type;

	// TODO Refator and remove the field Class<?> cls. It's possible using
	// FieldObject?
	public Column(String field, Class<?> cls) {
		this(field, MIN_WIDTH, cls);
	}

	public Column(String field, String title, Class<?> cls) {
		this(field, title, MIN_WIDTH, false, cls);
	}

	public Column(String field, int width, Class<?> cls) {
		this(field, field, width, false, cls);
	}
	
	public Column(String field, boolean editable, Class<?> cls) {
		this(field, field, MIN_WIDTH, editable, cls);
	}
	
	public Column(String field, int width, boolean editable, Class<?> cls) {
		this(field, field, width, editable, cls);
	}	
	
	public Column(String field, String title, boolean editable, Class<?> cls) {
		this(field, title, MIN_WIDTH, editable, cls);
	}

	public Column(String field, String title, int width, boolean editable, Class<?> cls) {
		
		if(field == null || cls == null){
			throw new InvalidParameterException("Parameters field & cls cannot be null. They must match with fields of your object class.");
		}
		if(width < MIN_WIDTH){
			throw new InvalidParameterException("Width must be > 0.");
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
		return "[Class=" + type.getName() + ", Title=" + title + ", Editable=" + editable + "]";
	}
}
