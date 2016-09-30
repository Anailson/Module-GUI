package components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

@SuppressWarnings("serial")

public class LabelTextField extends LabelComponent{
			
	private HintTextField textField;
	
	public LabelTextField(int minWidth, String label, String [] values) {
		super(minWidth, label, values);
	}
	
	@Override
	public Object save() {
		
		return null;
	}
	
	@Override
	public void clear() {
		textField.clear();
	}
	
	@Override
	protected JComponent getComponent(String[] values) {
		return textField = new HintTextField(values);
	}

	private class HintTextField extends JTextField implements FocusListener {

		private String hint;
		private boolean showingHint;

		public HintTextField(String [] values) {
			hint = getHint(values);
			showingHint = true;

			setText(hint);
			setForeground(Color.LIGHT_GRAY);
			addFocusListener(this);
		}
		
		public void clear(){
			setText(hint);
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

