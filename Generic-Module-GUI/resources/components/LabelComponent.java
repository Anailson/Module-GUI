package components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

import annotation.RowElement;
import genericObject.GenericField;

@SuppressWarnings("serial")
public abstract class LabelComponent extends JComponent {

	private final int GAP = 5;

	private GenericField gField;
	private JLabel label;
	private JComponent component;


	public LabelComponent(GenericField gField, int minWidth, String label, String[] values) {
		this.gField = gField;
		this.label = new JLabel(label);
		this.component = getComponent(values);

		addComponents(minWidth);
	}
	
	public GenericField getField() {
		return gField;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public boolean isRequired(){ 
		
		RowElement element = (RowElement) gField.getAnnotation();
		
		if(element.required()){
			if(isEmpty()){
				return true;
			}
		}
		return false;
	}

	public abstract Object save();

	public abstract void clear();

	protected abstract boolean isEmpty();

	protected abstract JComponent getComponent(String[] values);

	private void addComponents(int minWidth) {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		label.setPreferredSize(new Dimension(label.getPreferredSize().width + GAP, label.getPreferredSize().height));

		if (minWidth > 0) {
			component.setPreferredSize(new Dimension(minWidth, component.getPreferredSize().height));
		}

		this.add(label);
		this.add(component);
	}
}