package view;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.GenericFormController;
import interfaces.GenericFormListener;

@SuppressWarnings("serial")
public class GenericForm extends JPanel {

	private JButton btnSave, btnClear, btnCancel;
	private GenericFormController controller;

	public GenericForm(int gap, Class<?>... clss) {

		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		this.btnSave = new JButton("Save");
		this.btnClear = new JButton("Clear fields");
		this.btnCancel = new JButton("Cancel");
		this.controller = new GenericFormController(btnSave, btnClear, btnCancel);

		btnSave.addActionListener(controller);
		btnClear.addActionListener(controller);
		btnCancel.addActionListener(controller);

		generateSessionForms(gap, clss);
	}

	public void addGenericFormListener(GenericFormListener listener) {
		if (listener == null) {
			throw new NullPointerException("GenericFormListener must be != null");
		}
		controller.addGenericFormListener(listener);
	}

	private void generateSessionForms(int gap, Class<?>[] classes) {

		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1;
		constraint.insets = new Insets(gap, gap, 0, gap);

		int y = 0;
		for (Class<?> cls : classes) {

			constraint.gridy = y++;
			SessionFormPanel sessionForm = new SessionFormPanel(gap, cls);
			add(sessionForm, constraint);
			controller.addSessionForm(sessionForm);
		}

		constraint.gridy = y;
		constraint.ipady = gap;
		add(sessionButtons(), constraint);
	}

	private JPanel sessionButtons() {
		JPanel buttonPanel = new JPanel();

		buttonPanel.add(btnSave);
		buttonPanel.add(btnClear);
		buttonPanel.add(btnCancel);

		return buttonPanel;
	}
}