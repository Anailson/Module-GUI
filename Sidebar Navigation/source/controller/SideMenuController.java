package controller;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import interfaces.SideMenuListener;
import model.Settings;
import model.SideMenu;
import view.FloatSideBarFactory;

public class SideMenuController {

	private int nivel;
	private SideMenuListener listener;

	public SideMenuController(int nivel, SideMenuListener listener) {
		
		this.nivel = nivel;
		this.listener = listener;
	}

	public MouseListener getMouseListener(SideMenu menu) {
		return new MenuMouseListener(menu);
	}

	public class MenuMouseListener implements MouseListener{

		private SideMenu menu;
		private FloatSideBarFactory factory;
		
		public MenuMouseListener(SideMenu menu) {
			this.menu = menu;
			this.factory = FloatSideBarFactory.getInstance(listener);
		}
		
		@Override
		public void mouseClicked(MouseEvent event){}

		@Override
		public void mouseEntered(MouseEvent event) {
			enteredMenu(event.getComponent());
		}

		@Override
		public void mouseExited(MouseEvent event) {
			exitedMenu();
		}

		@Override
		public void mousePressed(MouseEvent event){
			pressedMenu();
		}

		@Override
		public void mouseReleased(MouseEvent event){
			releasedMenu();
		}

		private void enteredMenu(Component c) {

			//TODO set 
			Settings set = Settings.getInstance();
			menu.setOpaque(true);
			menu.setBackground(set.getSelectedMenuColor());
			menu.setForeground(set.getSelectedTextColor());
			showFloatMenu(c);
			listener.enteredMenu(menu);
		}

		private void exitedMenu() {

			Settings set = Settings.getInstance();
			menu.setOpaque(true);
			menu.setBackground(set.getDefaultMenuColor());
			menu.setForeground(set.getDefaultTextColor());
			listener.exitedMenu(menu);
		}
		
		private void pressedMenu(){
			listener.pressedMenu(menu);
		}
		
		private void releasedMenu(){
			listener.releasedMenu(menu);
		}

		private void showFloatMenu(Component c) {

			factory.removeFloadSideBar(nivel);
			if (menu.hasSubMenu()) {
				factory.addFloatSideBar(nivel, c.getLocationOnScreen(), menu);
			}
		}
	}
}
