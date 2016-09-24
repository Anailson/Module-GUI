package components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LabelComponent extends JComponent {

	private JComponent field;

	public LabelComponent(int gap, String label, JComponent field) {
		this.field = field;
		
		addComponents(gap, label);
	}

	public JComponent getField() {
		return field;
	}
	
	private void addComponents(int gap, String text){

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel(text);
		int width = label.getPreferredSize().width + gap / 5;
		label.setPreferredSize(new Dimension(width, label.getPreferredSize().height));
		
		this.add(label);
		this.add(field);
		
	}
}