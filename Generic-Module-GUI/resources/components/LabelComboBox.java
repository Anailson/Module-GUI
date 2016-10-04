package components;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import genericObject.GenericField;

@SuppressWarnings("serial")
public class LabelComboBox extends LabelComponent{

	private JComboBox<String> comboBox;
	
	public LabelComboBox(GenericField gField, int minWidth, String label, String[] values) {
		super(gField, minWidth, label, values);
	}

	@Override
	public void clear() {
		
		comboBox.setSelectedIndex(0);
	}

	@Override
	public Object save() {
		return comboBox.getSelectedItem();
	}
	
	@Override
	public boolean isEmpty() {
		if(comboBox.getSelectedIndex() == 0){
			return true;
		}
		return false;
	}
	
	@Override
	protected JComponent getComponent(String[] values){
		return comboBox = new JComboBox<String>(values);
	}
}