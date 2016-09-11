package model;

import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class SideMenu extends JLabel{

	private String text;
	private ArrayList<SideMenu> subMenus;

	public SideMenu(String text) {
		super(text);
		this.text = text;
		this.subMenus = new ArrayList<>();

		setDefaultColor();
	}
	
	public SideMenu(String text, String tip) {
		this(text);
		setToolTipText(tip);
	}
	
	public ArrayList<SideMenu> getSubMenus(){
		return subMenus;
	}
	
	private void setDefaultColor(){

		Settings set = Settings.getInstance();
		setOpaque(true);
		setBackground(set.getDefaultMenuColor());
		setForeground(set.getDefaultTextColor());
	}
	
	public boolean addMenu(SideMenu menu) {
		return subMenus.add(menu);
	}

	public boolean addMenu(String text){
		return addMenu(new SideMenu(text));
	}
	
	public boolean addMenu(String text, String tip){
		return addMenu(new SideMenu(text, tip));
	}
	
	public boolean hasSubMenu(){
		
		if(subMenus.size() > 0){
			return true;
		}	
		return false;
	}
	
	@Override
	public String toString() {
		return text + " <" +getClass().getName() + ">";
	}
}