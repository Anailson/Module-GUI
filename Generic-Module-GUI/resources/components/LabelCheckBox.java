package components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LabelCheckBox extends LabelComponent{

	public LabelCheckBox(int minWidth, String label, String[] values) {
		super(minWidth, label, new GroupCheckBox(values));
	}
	
	private static class GroupCheckBox extends JComponent{
		
		private ArrayList<JCheckBox> checks;
		
		public GroupCheckBox(String [] values) {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			
			checks = new ArrayList<>();
			int maxWidth = 0;
			for(String s : values){
				JCheckBox check = new JCheckBox(s);
				maxWidth = Math.max(maxWidth, check.getPreferredSize().width);
				checks.add(check);
			}
			
			for(JCheckBox check : checks){
				check.setPreferredSize(new Dimension(maxWidth, check.getPreferredSize().height));
				add(check);
			}
		}		
	}		
}