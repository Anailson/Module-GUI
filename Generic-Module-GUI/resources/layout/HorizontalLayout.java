package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

import components.LabelComponent;

public class HorizontalLayout implements LayoutManager2 {
	private static int maxWidthRow;
	private boolean sizeUnknown;
	private int gap, lastRow, nRows;
	private int prefWidth, prefHeight, minWidth, minHeight, maxHeightRow;
	private HashMap<LabelComponent, HorizontalLayoutConstraint> constraints;

	public HorizontalLayout(int gap) {

		this.gap = gap;
		sizeUnknown = true;
		prefWidth = prefHeight = minWidth = minHeight = nRows = maxHeightRow = maxWidthRow = 0;
		lastRow = -1;
		constraints = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(Component parent, Object obj) {

		if (obj != null) {

			maxHeightRow = Math.max(maxHeightRow, parent.getPreferredSize().height);
			HorizontalLayoutConstraint constraint = (HorizontalLayoutConstraint) obj;
			constraints.put((LabelComponent) parent, constraint);

			if (lastRow != constraint.getRow()) {
				nRows = (lastRow = constraint.getRow()) + 1;
			}
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
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
		return new Dimension(prefWidth, 200);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		if (sizeUnknown) {
			setSize(parent);
		}

		Insets insets = parent.getInsets();
		int width = prefWidth + insets.left + insets.right;
		int height = prefHeight + insets.top + insets.bottom;

		return new Dimension(width, height);
	}

	@Override
	public void layoutContainer(Container parent) {
		
		if (prefWidth != parent.getWidth()) {
			resizeComponents(parent);
		}

		int x = gap, y = gap, row = -1;
		
		for (Component component : parent.getComponents()) {

			LabelComponent lComp = (LabelComponent) component;
			
			if (lComp.isVisible()) {
				HorizontalLayoutConstraint constraint = constraints.get(lComp);

				if (row != constraint.getRow()) { // next line...
					row = constraint.getRow();
					x = gap;
					y = gap + (maxHeightRow + gap) * row;
				}

				int yFake = y + Math.subtractExact(maxHeightRow, lComp.getPreferredSize().height) / 2;
				
				Dimension d = lComp.getPreferredSize();
				lComp.setBounds(x, yFake, d.width, d.height);
				x += d.width + gap;
			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container parent) {
		return Component.RIGHT_ALIGNMENT;
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		return Component.TOP_ALIGNMENT;
	}

	@Override
	public void invalidateLayout(Container parent) {
	}

	private void setSize(Container parent) {
		
		int maxWidth = 0, row = -1;

		for (Component lComp : parent.getComponents()) {
			
			if (lComp.isVisible()) {
				
				if (row != constraints.get(lComp).getRow()) {					
					row = constraints.get(lComp).getRow();
					maxWidth = gap;
				}

				maxWidth += lComp.getPreferredSize().width + gap;
				maxWidthRow = Math.max(maxWidth, maxWidthRow);
			}
		}

		minHeight = prefHeight = (maxHeightRow + gap) * nRows;
		minWidth = prefWidth = maxWidthRow = Math.max(maxWidth, maxWidthRow);
		minHeight = prefHeight;
		sizeUnknown = false;
	}

	private void resizeComponents(Container parent) {

		
		for (Component comp : parent.getComponents()) {

			HorizontalLayoutConstraint cons = constraints.get(comp);

			if (cons.isResizable()) {

				if (comp.isVisible()) {
					
					if(cons.getMinWidth() == 0){
						int width = prefWidth - 2 * gap;
						comp.setPreferredSize(new Dimension(width, comp.getPreferredSize().height));
					} 

					Dimension d = comp.getPreferredSize();
					int expectedWidth = getExpectedWidth(d.width, parent.getWidth(), cons.getRow());

					if (expectedWidth > comp.getMinimumSize().width) {
						d.width = expectedWidth;
					} else {
						d.width = comp.getMinimumSize().width;
					}
					comp.setPreferredSize(d);
					
				}
			}
		}

		prefWidth = Math.max(parent.getWidth(), minWidth);
	}

	private int getExpectedWidth(int compWidth, int parentWidth, int row) {
		return compWidth + getAddWidth(parentWidth) / getResizableComponents(row);
	}

	private int getAddWidth(int width) {
		int add = 0;
		if(prefWidth > width){
			add -= prefWidth - width;
		} else {
			add += width - prefWidth;
		}
		
		return add;
	}

	private int getResizableComponents(int row) {

		int nComps = 0;
		for (HorizontalLayoutConstraint cons : constraints.values()) {

			if (cons.getRow() == row) {

				if (cons.isResizable()) {
					nComps++;
				}
			}
		}
		return nComps;
	}
}
