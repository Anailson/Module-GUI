package components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LabelComponent extends JComponent {

	private JComponent field;

	public LabelComponent(int minWidth, String label, JComponent field) {
		this.field = field;

		addComponents(minWidth, label);
	}

	public JComponent getField() {
		return field;
	}

	private void addComponents(int minWidth, String text) {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(label.getPreferredSize().width + 5, label.getPreferredSize().height));
		
		if (minWidth > 0){
			field.setPreferredSize(new Dimension(minWidth, field.getPreferredSize().height));
		}

		this.add(label);
		this.add(field);
	}
}