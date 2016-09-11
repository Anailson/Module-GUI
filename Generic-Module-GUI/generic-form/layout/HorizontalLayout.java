package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;

public class HorizontalLayout implements LayoutManager2 {

	private boolean sizeUnknown;
	private int vgap;
	private int prefWidth, prefHeight, minWidth, minHeight, nResizable;
	private ArrayList<Boolean> resizables;
	
	public HorizontalLayout() {
		this(20);
	}
	
	public HorizontalLayout(int gap) {
		vgap = gap;
		sizeUnknown = true;
		prefWidth = prefHeight = minWidth = minHeight = nResizable = 0;
		resizables = new ArrayList<>();
	}


	@Override
	public void addLayoutComponent(Component parent, Object constraint) {

		if(constraint != null){
			
			Boolean resize = (Boolean) constraint;
			resizables.add(resize);
			if(resize){
				nResizable++;
			}			
		} else{
			resizables.add(false);
		}
	}	

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public void layoutContainer(Container parent) {
		
		int x = vgap, y = vgap;
		
		if(prefWidth != parent.getWidth()){
			resizeComponents(parent);
		}
		
		for(Component c : parent.getComponents()){
			
			if (c.isVisible()){
				Dimension d = c.getPreferredSize();
				c.setBounds(x, y, d.width, d.height);
				x += d.width + vgap;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		
		Insets insets = parent.getInsets();
		int width = minWidth + insets.left + insets.right;
		int height = minHeight + insets.top + insets.bottom;
		
		return new Dimension(width, height);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
	
		System.out.println("maximumLayoutSize() 1000x900" );
		return new Dimension(1000, 900);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
	
		setSize(parent);
		
		Insets insets = parent.getInsets();
		int width = prefWidth + insets.left + insets.right;
		int height = prefHeight + insets.top + insets.bottom;
		
		return new Dimension(width, height);
	}

	@Override
	public float getLayoutAlignmentX(Container parent) {
		return parent.getAlignmentX();
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		return parent.getAlignmentY();
	}

	@Override
	public void invalidateLayout(Container parent) {

	}
	
	private void setSize(Container parent){

		int nComps = parent.getComponentCount();
		prefWidth = (nComps + 1) * vgap;
		
		for (Component c : parent.getComponents()) {
			
			if (c.isVisible()){
				
				prefWidth += c.getPreferredSize().width;
				
				if(sizeUnknown){
					prefHeight = c.getPreferredSize().height + 2 * vgap;
					sizeUnknown = false;
				}
			}
		}
		
		minWidth = prefWidth;
		minHeight = prefHeight;
	}
	private void resizeComponents(Container parent){

		int addWidth = 0;
		
		if(prefWidth > parent.getWidth()){
			//Adicional negativo
			addWidth -= prefWidth - parent.getWidth();
		} else {
			//Adicional positivo
			addWidth += parent.getWidth() - prefWidth;
		}
		
		for (int i = 0; i < parent.getComponentCount(); i++) {
			
			if(resizables.get(i)){
				
				Component c = parent.getComponent(i);	
			
				if(c.isVisible()){
				
					Dimension d = c.getPreferredSize();
					int previsousWidth = d.width + addWidth / nResizable;
					
					if(previsousWidth > c.getMinimumSize().width){
						d.width = previsousWidth;
					} else {
						d.width = c.getMinimumSize().width;
					}
					c.setPreferredSize(d);
				}  
			}
		}
		
		if(parent.getWidth() > minWidth){
			prefWidth = parent.getWidth();
		} else {
			prefWidth = minWidth; 
		}
	}
}
