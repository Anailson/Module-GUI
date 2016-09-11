package model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author Anailson Santos Mota
 * 
 *         This class has all settings used on SideMenus. Using this class, you
 *         can setup width, height, alignment, orientation, gap, margin of
 *         menus.
 * 
 */

public class Settings {

	public static final Alignment TOP = Alignment.TOP;
	public static final Alignment LEFT = Alignment.LEFT;
	public static final Alignment BOTTOM = Alignment.BOTTOM;
	public static final Alignment RIGHT = Alignment.RIGHT;
	public static final Orientation VERTICAL = Orientation.VERTICAL;
	public static final Orientation HORIZONTAL = Orientation.HORIZONTAL;

	private boolean menuCreated;
	private Dimension dimension;
	private int margin;
	private int gap;
	private Orientation orientation;
	private Alignment alignment;
	private Color selectedMenuColor, defaultMenuColor, selectedTextColor, defaultTextColor, backgroundColor;

	private static Settings settings = null;

	public enum Alignment {
		TOP, LEFT, BOTTOM, RIGHT;
	}

	public enum Orientation {
		VERTICAL, HORIZONTAL;
	}

	private Settings() {
		menuCreated = false;
		dimension = new Dimension(200, 50);
		margin = 10;
		gap = 10;
		orientation = Orientation.VERTICAL;
		alignment = Alignment.LEFT;
		backgroundColor = Color.LIGHT_GRAY;
		selectedMenuColor = Color.GRAY;
		defaultMenuColor = Color.WHITE;
		selectedTextColor = Color.WHITE;
		defaultTextColor = Color.BLACK;
	}

	/**
	 * Uses unique instance for Settings class.
	 * 
	 * @return Unique instance for Settings class.
	 */
	public static Settings getInstance() {

		if (settings == null) {
			settings = new Settings();
		}
		return settings;
	}

	public boolean isMenuCreated() {
		return menuCreated;
	}

	public void setMenuCreated() {
		this.menuCreated = true;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void setDimension(Dimension dimension) {
		if (dimension == null) {
			throw new IllegalArgumentException("Dimension must be != null.");
		}
		this.dimension = dimension;
	}

	public int getWidth(){
		return dimension.width;
	}
	
	/**
	 * Set width default for all menus.
	 * 
	 * @param width
	 *            - Width default for menus.
	 * @throws IllegalArgumentException
	 *             if width < 0
	 */
	public void setWidth(int width) {
		if (width < 0) {
			throw new IllegalArgumentException("Width must be > 0: (" + width + ")");
		}
		dimension.setSize(width, dimension.getHeight());
	}

	public int getHeight(){
		return dimension.height;
		
	}
	public void setHeight(int height) {
		if (height < 0) {
			throw new IllegalArgumentException("Height must be > 0: (" + height + ")");
		}
		dimension.setSize(dimension.getWidth(), height);
	}

	public int getMargin() {
		return margin;
	}
	
	public void setMargin(int margin){
		if (margin < 0) {
			throw new IllegalArgumentException("Margin must be > 0: (" + margin + ")");
		}
		this.margin = margin;
	}
	
	public int getGap() {
		return gap;
	}
	
	public void setGap(int gap) {

		if (gap < 0) {
			throw new IllegalArgumentException("Gap must be > 0: (" + gap + ")");
		}
		this.gap = gap;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Set orientation default to all menus. Settings.VERTICAL or
	 * Settings.HORIZONTAL. Vertical is default orientation.
	 * 
	 * @param orientation
	 *            - Orientation default to all menus.
	 * 
	 */
	public void setOrientation(Orientation orientation) {
		if (orientation == null) {
			throw new IllegalArgumentException(
					"Orientation must be != null. Uses Settings.VERTICAL or Settings.HORIZONTAL orientation.");
		}
		this.orientation = orientation;
	}	

	public Alignment getAlignment() {
		return alignment;
	}

	/**
	 * Set alignment default to all menus. Settings.TOP, Settings.LEFT,
	 * Settings.BOTTOM or Settings.RIGHT. Left is default alignment.
	 * 
	 * @param alignment
	 *            Alignment default to all menus.
	 * @throws IllegalArgumentException
	 *             If Alignment was null.
	 */
	public void setAlignment(Alignment alignment) {
		if (alignment == null) {
			throw new IllegalArgumentException(
					"Alignment must be != null. Uses Settings.TOP, Settings.LEFT, Settings.BOTTOM or Settings.RIGHT alignment.");
		}
		this.alignment = alignment;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color color) {
		if(color == null){
			backgroundColor = Color.WHITE;
			return;
		}
		this.backgroundColor = color;
	}	

	public Color getSelectedMenuColor() {
		return selectedMenuColor;
	}

	public void setSelectedMenuColor(Color color) {
		if(color == null){
			selectedMenuColor = new Color(210, 210, 210);
			return;
		}
		this.selectedMenuColor = color;
	}

	public Color getDefaultMenuColor() {
		return defaultMenuColor;
	}

	public void setDefaultMenuColor(Color color) {
		if(color == null){
			defaultMenuColor = Color.WHITE;
			return;
		}
		this.defaultMenuColor = color;
	}

	public Color getSelectedTextColor() {
		return selectedTextColor;
	}

	public void setSelectedTextColor(Color color) {

		if(color == null){
			selectedTextColor = Color.BLACK;
			return;
		}
		this.selectedTextColor = color;
	}

	public Color getDefaultTextColor() {
		return defaultTextColor;
	}

	public void setDefaulTextColor(Color color) {
		if(color == null){
			defaultTextColor = Color.BLACK;
			return;
		}
		this.defaultTextColor = color;
	}
}
