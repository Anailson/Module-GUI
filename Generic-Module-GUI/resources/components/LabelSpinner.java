package components;

import javax.swing.JComponent;
import javax.swing.JSpinner;

@SuppressWarnings("serial")
public class LabelSpinner extends LabelComponent {

	private JSpinner spinner;
	
	public LabelSpinner(int minWidth, String label) {

		super(minWidth, label, new String[]{});		
	}
	
	@Override
	public Object save() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void clear() {
		//TODO How to set default value
		spinner.setValue(0);
	}
	
	@Override
	protected JComponent getComponent(String[] values) {
		return spinner = new JSpinner();
	}
}