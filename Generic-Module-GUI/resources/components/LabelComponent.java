package components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class LabelComponent extends JComponent {

	private JComponent field;

	public LabelComponent(int minWidth, String label, String [] values) {
		this.field = getComponent(values);

		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		addComponents(minWidth, label);
	}

	public abstract Object save();
	
	public abstract void clear();
	
	protected abstract JComponent getComponent(String [] values); 
	
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