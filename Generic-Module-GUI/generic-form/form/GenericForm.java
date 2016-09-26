package form;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GenericForm extends JPanel implements ActionListener {

	private JButton btnSave, btnClear, btnCancel;

	public GenericForm(int gap, Class<?>... classes) {

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.RED));

		btnSave = new JButton("Save");
		btnClear = new JButton("Clear fields");
		btnCancel = new JButton("Cancel");

		btnSave.addActionListener(this);
		btnClear.addActionListener(this);
		btnCancel.addActionListener(this);

		generateSessionForms(gap, classes);
	}

	private void generateSessionForms(int gap, Class<?>[] classes) {
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.REMAINDER;
		int y = 0;
		for (Class<?> cls : classes) {

			c.gridy = y++;
			JPanel session = new SessionFormPanel(gap, cls);
			System.out.println(session.getPreferredSize().width + " " + getPreferredSize().width);
			add(session, c);
		}

		c.gridy = y;
		add(sessionButtons(), c);
	}

	private JPanel sessionButtons() {

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		buttonPanel.add(btnSave);
		buttonPanel.add(btnClear);
		buttonPanel.add(btnCancel);
		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource().equals(btnSave)) {

			System.out.println(btnSave.getText());

		} else if (event.getSource().equals(btnClear)) {

			System.out.println(btnClear.getText());

		} else if (event.getSource().equals(btnCancel)) {

			System.out.println(btnCancel.getText());
		}
	}
}