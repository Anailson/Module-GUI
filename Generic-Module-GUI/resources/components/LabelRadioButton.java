package components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class LabelRadioButton extends LabelComponent{
	
	public LabelRadioButton(int gap, String label, String[] values) {
		super(gap, label, new GroupRadioButton(values));
	}

	private static class GroupRadioButton extends JPanel{		
		
		private ButtonGroup group;
		private ArrayList<JRadioButton> radios;
		
		public GroupRadioButton(String [] values) {
			super(new FlowLayout(FlowLayout.LEFT));

			group = new ButtonGroup();
			radios = new ArrayList<>();
			
			int maxWidth = 0;
			for(String s : values){

				JRadioButton button = new JRadioButton(s);
				maxWidth = Math.max(maxWidth, button.getPreferredSize().width);
				group.add(button);
				radios.add(button);
			}
			
			for(JRadioButton button : radios){
				button.setPreferredSize(new Dimension(maxWidth, button.getPreferredSize().height));
				add(button);
			}
		}		
	}
}
