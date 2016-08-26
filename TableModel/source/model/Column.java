package model;

public class Column{
	private String title;
	private boolean editable;
	private Class<?> type;
	
	public Column(String title) {
		this(title, Object.class);
	}
	
	public Column(String title, Class<?> cls) {
		this(title, cls, false);
	}

	public Column(String title, boolean editable) {
		this(title, Object.class, editable);
	}
	
	public Column(String txt, Class<?> cls, boolean editable) {
		this.title = txt;
		this.type = cls;
		this.editable = editable;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	@Override
	public String toString() {
		return "<" + type.getName() + ">. Title: " + title + ". Editable: " + editable;
	}
}
