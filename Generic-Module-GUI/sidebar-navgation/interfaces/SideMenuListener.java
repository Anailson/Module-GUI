package interfaces;

import model.SideMenu;

public abstract class SideMenuListener {
	public abstract void exitedMenu(SideMenu menu);
	public abstract void enteredMenu(SideMenu menu);
	public abstract void releasedMenu(SideMenu menu);
	public abstract void pressedMenu(SideMenu menu);
}
