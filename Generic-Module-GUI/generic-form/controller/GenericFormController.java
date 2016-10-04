package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import components.LabelComponent;
import interfaces.GenericFormListener;
import view.SessionFormPanel;

public class GenericFormController implements ActionListener {

	private ArrayList<SessionFormPanel> sessionForms;
	private JButton btnSave, btnClear, btnCancel;
	private GenericFormListener listener;

	public GenericFormController(JButton btnSave, JButton btnClear, JButton btnCancel) {

		this.sessionForms = new ArrayList<>();
		this.btnSave = btnSave;
		this.btnClear = btnClear;
		this.btnCancel = btnCancel;
		this.listener = getListener();
	}

	public void addGenericFormListener(GenericFormListener listener) {
		this.listener = listener;
	}

	public boolean addSessionForm(SessionFormPanel sessionForm) {
		return sessionForms.add(sessionForm);
	}

	private void save() {

		boolean required = false;
		HashMap<String, Object> map = new HashMap<>();

		for (SessionFormPanel form : sessionForms) {

			for (Component component : form.getComponents()) {

				LabelComponent lComponent = (LabelComponent) component;

				if (lComponent.isRequired()) {
					lComponent.getLabel().setForeground(Color.RED);
					required = true;
				} else {
					map.put(lComponent.getField().getFieldName(), lComponent.save());
				}
			}
		}
		if(required){
			listener.isRequired();
			return;
		}
		listener.save(map);
	}

	private void clear() {

		for (SessionFormPanel form : sessionForms) {

			for (Component component : form.getComponents()) {
				LabelComponent lComponent = (LabelComponent) component;
				lComponent.clear();
				lComponent.getLabel().setForeground(Color.BLACK);
			}
		}
		listener.clear();
	}

	private void cancel() {
		listener.cancel();
	}

	private GenericFormListener getListener() {
		return new GenericFormListener() {

			@Override
			public void save(HashMap<String, Object> map) {
			}

			@Override
			public void clear() {
			}

			@Override
			public void cancel() {
			}

			public void isRequired() {
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource().equals(btnSave)) {

			save();

		} else if (event.getSource().equals(btnClear)) {

			clear();

		} else if (event.getSource().equals(btnCancel)) {

			cancel();
		}
	}
}
