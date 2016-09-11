package view;
import javax.swing.JPanel;

import interfaces.SideMenuListener;
import model.SideMenu;

public class SideNavigationMenu {

	private SideMenu menu;
	private SideMenuListener listener;
	
	public SideNavigationMenu(){
		menu = new SideMenu("mainMenu");
		listener = new SideMenuListener(){

			@Override
			public void pressedMenu(SideMenu menu) {}
			
			@Override
			public void releasedMenu(SideMenu menu) {}
			
			@Override
			public void enteredMenu(SideMenu menu) {}
			
			@Override
			public void exitedMenu(SideMenu menu) {}
		};
	}
	
	public boolean addMenu(SideMenu menu) {		
		return this.menu.addMenu(menu);
	}

	public boolean addMenu(String text){
		return addMenu(new SideMenu(text));
	}
	
	public boolean addMenu(String text, String tip){
		return addMenu(new SideMenu(text, tip));
	}
	
	public void addSideMenuListener(SideMenuListener listener){
		this.listener = listener;
	}
	
	public JPanel generate(){

		FloatSideBarFactory factory = FloatSideBarFactory.getInstance(listener);
		return factory.getSideBar(0, menu);		
	}
}