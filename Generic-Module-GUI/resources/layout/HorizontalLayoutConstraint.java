package layout;

import java.lang.annotation.Annotation;

import annotation.RowElement;

public class HorizontalLayoutConstraint {

	private RowElement element;
	
	public HorizontalLayoutConstraint(int row){
		this(rowElement(row));
	}

	public HorizontalLayoutConstraint(RowElement element) {
		this.element = element;
	}

	public int getRow() {
		return element.index();
	}

	public boolean isResizable() {
		return element.resizable();
	}

	public boolean isRequired() {
		return element.required();
	}
	
	private static RowElement rowElement(int row){
		
		return new RowElement(){
			@Override
			public Class<? extends Annotation> annotationType() {
				return RowElement.class;
			}
			
			@Override
			public int index() {
				return row;
			}
			
			@Override
			public boolean required() {
				return false;
			}
			
			@Override
			public boolean resizable() {
				return false;
			}
		};
	}
}