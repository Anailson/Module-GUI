package components;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LabelComboBox extends LabelComponent{

	public LabelComboBox(int minWidth, String label, String[] values) {
		super(minWidth, label, new JComboBox<Object>(values));
	}
}