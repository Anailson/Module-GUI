package components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import genericObject.GenericField;

@SuppressWarnings("serial")

public class LabelTextField extends LabelComponent{
			
	private HintTextField textField;
	
	public LabelTextField(GenericField gField, int minWidth, String label, String [] values) {
		super(gField, minWidth, label, values);
	}
	
	@Override
	public Object save() {
		return textField.save();
	}
	
	@Override
	public void clear() {
		textField.clear();
	}
	
	@Override
	protected boolean isEmpty() {

		return textField.isEmpty();
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
			clear();
			addFocusListener(this);
		}
		
		public String save(){
			return getText();
		}
		
		public void clear(){
			showingHint = true;
			setText(hint);
			setForeground(Color.LIGHT_GRAY);
		}
		
		public boolean isEmpty(){
			return getText().isEmpty();
		}

		@Override
		public void focusGained(FocusEvent e) {

			if (getText().isEmpty()) {
				setForeground(Color.BLACK);
				showingHint = false;
				super.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {

			if (getText().isEmpty()) {
				clear();
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

