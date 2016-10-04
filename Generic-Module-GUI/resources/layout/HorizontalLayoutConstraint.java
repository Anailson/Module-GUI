package layout;

import java.lang.annotation.Annotation;

import javax.swing.JTextField;

import annotation.RowElement;
import annotation.RowElementSettings;
import genericObject.GenericField;

public class HorizontalLayoutConstraint {

	private RowElement element;
	private int minWidth;
	private String title, typeClass;
	private String[] values;

	public HorizontalLayoutConstraint(GenericField field) {

		RowElementSettings settings = getSettings(field);
		
		element = (RowElement) field.getAnnotation();
		title = getTitle(settings);
		typeClass = settings.typeClass();
		minWidth = settings.minWidth();
		values = settings.values();	
	}
	
	public int getRow(){
		return element.index();
	}
	
	public boolean isResizable(){
		return element.resizable();
	}
	
	public boolean isRequired(){
		return element.required();
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getTypeClass() {
		return typeClass;
	}
	
	public int getMinWidth() {
		return minWidth;
	}
	
	public String[] getValues() {
		return values;
	}

	private RowElementSettings getSettings(GenericField field) {

		RowElementSettings settings = field.getField().getAnnotation(RowElementSettings.class);
		
		if (settings == null) {
			
			settings = new RowElementSettings() {
				
				@Override
				public Class<? extends Annotation> annotationType() {
					return RowElementSettings.class;
				}

				@Override
				public int minWidth() {
					return 0;
				}

				@Override
				public String title() {
					return field.getFieldName();
				}

				@Override
				public String typeClass() {
					return JTextField.class.getSimpleName();
				}

				@Override
				public String[] values() {
					return new String[] {};
				}
			};
		}
		return settings;
	}

	private String getTitle(RowElementSettings settings) {

		if (element.required()) {
			return settings.title() + " *";
		}

		return settings.title();
	}
}