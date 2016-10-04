package components;

import javax.swing.JComponent;
import javax.swing.JSpinner;

import genericObject.GenericField;

@SuppressWarnings("serial")
public class LabelSpinner extends LabelComponent {

	private JSpinner spinner;
	
	public LabelSpinner(GenericField gField, int minWidth, String label) {

		super(gField, minWidth, label, new String[]{});		
	}
	
	@Override
	public Object save() {
		
		return spinner.getValue();
	}
	
	@Override
	public void clear() {
		spinner.setValue(0);
	}
	
	@Override
	protected boolean isEmpty() {
		if(spinner.getValue().equals(0)){
			return true;
		}
		return false;
	}
	
	@Override
	protected JComponent getComponent(String[] values) {
		return spinner = new JSpinner();
	}
}