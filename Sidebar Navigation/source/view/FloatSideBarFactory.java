package view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import controller.SideMenuController;
import interfaces.SideMenuListener;
import model.Settings;
import model.SideMenu;

public class FloatSideBarFactory {

	private ArrayList<FloatSideBarView> sideBars;
	private SideMenuListener listener;
	private static FloatSideBarFactory factory = null;

	private FloatSideBarFactory(SideMenuListener listener) {
		this.listener = listener; 
		this.sideBars = new ArrayList<>();
	}

	public static FloatSideBarFactory getInstance(SideMenuListener listener) {

		if (factory == null) {
			factory = new FloatSideBarFactory(listener);
		}
		return factory;
	}
	
	protected SideBarView getSideBar(int nivel, SideMenu menu){
		SideBarView sideBar = new SideBarView(0);
		sideBar.generate(menu);
		return sideBar;
	}

	public boolean addFloatSideBar(int nivel, Point p, SideMenu menu) {
		FloatSideBarView floatBar = new FloatSideBarView(nivel, p);
		floatBar.generate(menu);
		return sideBars.add(floatBar);
	}

	public void removeFloadSideBar(int nivel) {

		for (int i = sideBars.size() - 1; i >= nivel; i--) {
			FloatSideBarView sideBar = sideBars.get(i);
			if (sideBar.isVisible()) {
				sideBar.dispose();
				sideBars.remove(i);
			}
		}
	}
	
	
	@SuppressWarnings("serial")
	public class SideBarView extends JPanel {

		private SideMenuController controller;
		private Settings set;
		
		private SideBarView(int nivel) {

			super(null);
			controller = new SideMenuController(nivel, listener);
			set = Settings.getInstance();
			setBackground(set.getBackgroundColor());
		}

		protected void generate(SideMenu menu) {
			
			if (!set.isMenuCreated()) {

				if (set.getAlignment() == Settings.LEFT || set.getAlignment() == Settings.RIGHT) {

					generateVerticalLayout(menu.getSubMenus());

				} else if (set.getAlignment() == Settings.TOP || set.getAlignment() == Settings.BOTTOM) {

					generateHorizontalLayout(menu.getSubMenus());
				}

				set.setMenuCreated();
			} else {

				if (set.getOrientation() == Settings.VERTICAL) {

					generateVerticalLayout(menu.getSubMenus());

				} else if (set.getOrientation() == Settings.HORIZONTAL) {

					generateHorizontalLayout(menu.getSubMenus());
				}
			}
		}

		private void generateVerticalLayout(ArrayList<SideMenu> menus) {

			int nButtons = menus.size();
			int mWidth = (int) set.getDimension().getWidth();
			int mHeight = (int) set.getDimension().getHeight();
			int margin = set.getMargin();
			int x = set.getMargin();
			int y = set.getMargin();
			int gap = set.getGap();

			for (SideMenu sideMenu : menus) {

				configSideMenu(sideMenu, x, y);
				y += mHeight + gap;
			}

			int pWidth = 2 * margin + mWidth;
			int pHeight = 2 * margin + nButtons * (mHeight + gap) - gap;

			setPreferredSize(new Dimension(pWidth, pHeight));
		}

		private void generateHorizontalLayout(ArrayList<SideMenu> menus) {

			int nButtons = menus.size();
			int mWidth = (int) set.getWidth();
			int mHeight = (int) set.getHeight();
			int margin = set.getMargin();
			int x = set.getMargin();
			int y = set.getMargin();
			int gap = set.getGap();

			for (SideMenu sideMenu : menus) {
				configSideMenu(sideMenu, x, y);
				x += mWidth + gap;
			}

			int pWidth = 2 * margin + nButtons * (mWidth + gap) - gap;
			int pHeight = 2 * margin + mHeight;
			setPreferredSize(new Dimension(pWidth, pHeight));
		}

		private void configSideMenu(SideMenu menu, int x, int y) {

			menu.setBounds(x, y, set.getWidth(), set.getHeight());
			menu.addMouseListener(controller.getMouseListener(menu));
			add(menu);
		}
	}
	
	@SuppressWarnings("serial")
	private class FloatSideBarView extends JDialog {

		private Point patternLocation;
		private int nivel;
		
		protected FloatSideBarView(int nivel, Point p) {
			super();
			this.nivel = nivel;
			this.patternLocation = p;
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			//setAlwaysOnTop(true);
			setUndecorated(true);
		}

		protected void generate(SideMenu menu) {
				
			if (menu.hasSubMenu()) {
				
				SideBarView sideBar = new SideBarView(nivel + 1);
				sideBar.generate(menu);

				add(sideBar);
				pack();
				setPosition(sideBar.getPreferredSize());
			}
		}

		private void setPosition(Dimension size) {

			Settings set = Settings.getInstance();

			int x = (int) patternLocation.getX();
			int y = (int) patternLocation.getY();

			switch (set.getAlignment()) {

			case LEFT:
				x += set.getWidth() + set.getMargin();
				break;
			case RIGHT:
				x -= (size.getWidth() + set.getMargin());
				break;
			case TOP:
				y += set.getHeight() + set.getMargin();
				break;
			case BOTTOM:
				y -= (size.getHeight() + set.getMargin());
				break;
			}
			setLocation(new Point(x, y));
			setVisible(true);
		}
	}
}
