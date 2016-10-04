package components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import genericObject.GenericField;

@SuppressWarnings("serial")
public class LabelCheckBox extends LabelComponent {

	private GroupCheckBox checkBoxs;

	public LabelCheckBox(GenericField gField, int minWidth, String label, String[] values) {
		super(gField, minWidth, label, values);
	}
	
	@Override
	public Object save() {
		return checkBoxs.save();
	}

	@Override
	public void clear() {
		checkBoxs.clear();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	protected JComponent getComponent(String[] values) {
		return checkBoxs = new GroupCheckBox(values);
	}

	private class GroupCheckBox extends JComponent {

		private ArrayList<JCheckBox> checks;

		private GroupCheckBox(String[] values) {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			checks = new ArrayList<>();
			int maxWidth = 0;
			for (String s : values) {
				JCheckBox check = new JCheckBox(s);
				maxWidth = Math.max(maxWidth, check.getPreferredSize().width);
				checks.add(check);
			}

			for (JCheckBox check : checks) {
				check.setPreferredSize(new Dimension(maxWidth, check.getPreferredSize().height));
				add(check);
			}
		}

		private boolean[] save() {

			boolean[] obj = new boolean[checks.size()];
			for (int i = 0; i < checks.size(); i++) {
				JCheckBox check = checks.get(i);

				if (check.isSelected()) {
					obj[i] = true;
				}
			}
			return obj;
		}

		private void clear() {

			for (JCheckBox check : checks) {
				check.setSelected(false);
			}
		}
	}
}