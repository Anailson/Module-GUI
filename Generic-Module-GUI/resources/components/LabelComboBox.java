package components;

import javax.swing.JComboBox;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LabelComboBox extends LabelComponent{

	private JComboBox<Object> comboBox;
	
	public LabelComboBox(int minWidth, String label, String[] values) {
		super(minWidth, label, values);
	}

	@Override
	public void clear() {
		comboBox.setSelectedIndex(0);
	}

	@Override
	public Object save() {
		return null;
	}
	
	@Override
	public JComponent getComponent(String[] values){
		return comboBox = new JComboBox<Object>(values);
	}
}