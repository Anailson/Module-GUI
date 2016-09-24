package components;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LabelComboBox extends LabelComponent{

	public LabelComboBox(int gap, String label, String[] values) {
		super(gap, label, new JComboBox<Object>(values));
	}
}
