package components;

import javax.swing.JSpinner;

@SuppressWarnings("serial")
public class LabelSpinner extends LabelComponent {

	public LabelSpinner(int minWidth, String label) {

		super(minWidth, label, new JSpinner());		
	}
}