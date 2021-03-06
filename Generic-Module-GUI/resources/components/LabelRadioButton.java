package components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import genericObject.GenericField;

@SuppressWarnings("serial")
public class LabelRadioButton extends LabelComponent {

	private GroupRadioButton radioButtons;

	public LabelRadioButton(GenericField gField, int minWidth, String label, String[] values) {
		super(gField, minWidth, label, values);
	}

	@Override
	public void clear() {
		radioButtons.clear();
	}

	@Override
	public Object save() {
		return radioButtons.save();
	}
	
	@Override
	protected boolean isEmpty() {
		return radioButtons.isEmpty();
	}

	@Override
	protected JComponent getComponent(String[] values) {
		return radioButtons = new GroupRadioButton(values);
	}

	private static class GroupRadioButton extends JComponent {

		private ButtonGroup group;
		private ArrayList<JRadioButton> radios;

		public GroupRadioButton(String[] values) {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			group = new ButtonGroup();
			radios = new ArrayList<>();

			int maxWidth = 0;
			for (String s : values) {

				JRadioButton button = new JRadioButton(s);
				maxWidth = Math.max(maxWidth, button.getPreferredSize().width);
				radios.add(button);
				group.add(button);
			}

			for (JRadioButton button : radios) {
				button.setPreferredSize(new Dimension(maxWidth, button.getPreferredSize().height));
				add(button);
			}
		}

		public <T extends Object> String save() {

			for (JRadioButton radio : radios) {
				if (radio.isSelected()) {
					return radio.getText();
				}
			}

			return "";
		}

		public void clear() {
			group.clearSelection();
		}
		
		public boolean isEmpty() {
			
			for(JRadioButton button : radios){
				if(button.isSelected()){
					return false;
				}
			}
			return true;
		}
	}
}
