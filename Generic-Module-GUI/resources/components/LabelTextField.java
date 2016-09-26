package components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")

public class LabelTextField extends LabelComponent{
			
	public LabelTextField(int minWidth, String label, String [] values) {
		super(minWidth, label, new HintTextField(values));
	}

	private static class HintTextField extends JTextField implements FocusListener {

		private String hint;
		private boolean showingHint;

		public HintTextField(String [] values) {
			hint = getHint(values);
			showingHint = true;

			setText(hint);
			setForeground(Color.LIGHT_GRAY);
			addFocusListener(this);
		}

		@Override
		public void focusGained(FocusEvent e) {
			setForeground(Color.BLACK);
			if (getText().isEmpty()) {
				setForeground(Color.BLACK);
				showingHint = false;
				super.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {

			if (getText().isEmpty()) {
				setForeground(Color.LIGHT_GRAY);
				showingHint = true;
				super.setText(hint);
			}
		}

		@Override
		public String getText() {
			return showingHint ? "" : super.getText();
		}
		
		private String getHint(String [] values){
			String hint = "";
			for(String s : values){
				hint += s + " ";
			}
			return hint;
		}
	}	
}

