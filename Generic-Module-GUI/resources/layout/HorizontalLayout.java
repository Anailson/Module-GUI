package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

public class HorizontalLayout implements LayoutManager2 {

	private boolean sizeUnknown;
	private int gap, lastRow, nRows;
	private int prefWidth, prefHeight, minWidth, minHeight, maxComponentHeight;
	private HashMap<Component, HorizontalLayoutConstraint> constraints;

	public HorizontalLayout(int gap) {

		this.gap = gap;
		sizeUnknown = true;
		prefWidth = prefHeight = minWidth = minHeight = nRows = maxComponentHeight = 0;
		lastRow = -1;
		constraints = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(Component parent, Object obj) {

		if (obj != null) {

			HorizontalLayoutConstraint constraint = (HorizontalLayoutConstraint) obj;
			constraints.put(parent, constraint);

			if (lastRow != constraint.getRow()) {
				nRows = (lastRow = constraint.getRow()) + 1;
			}

		} else {

			constraints.put(parent, new HorizontalLayoutConstraint(lastRow));
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

		return new Dimension(1000, 900);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		System.out.println(parent.getSize().width + " " + parent.getSize().height);
		
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

		for (Component comp : parent.getComponents()) {

			if (comp.isVisible()) {
				HorizontalLayoutConstraint layConst = constraints.get(comp);

				if (row != layConst.getRow()) { // next line...
					row = layConst.getRow();
					x = gap;
					y = gap + (maxComponentHeight + gap) * row;
				}
				
				Dimension d = comp.getPreferredSize();
				comp.setBounds(x, y, d.width, d.height);
				x += d.width + gap;
			}
		}
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

	private void setSize(Container parent) {

		prefWidth = parent.getComponentCount() * gap;// tem que corrigir

		for (Component c : parent.getComponents()) {

			if (c.isVisible()) {

				if (c.getMinimumSize().getHeight() > maxComponentHeight) {
					maxComponentHeight = c.getMinimumSize().height;
				}
				prefWidth += c.getPreferredSize().width;
			}
		}

		isHeightUnknow();

		minHeight = prefHeight;
		minWidth = (prefWidth += gap);
	}

	private void resizeComponents(Container parent) {

		for (Component comp : parent.getComponents()) {

			HorizontalLayoutConstraint cons = constraints.get(comp);

			if (cons.isResizable()) {

				if (comp.isVisible()) {

					Dimension d = comp.getPreferredSize();
					int expectedWidth = getExpectedWidth( d.width, parent.getWidth(), cons.getRow());

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

	private void isHeightUnknow() {

		if (prefHeight == 0) {
			prefHeight += gap + (maxComponentHeight + gap) * nRows;
			sizeUnknown = false;
		}
	}

	private int getExpectedWidth(int compWidth, int parentWidth, int row) {
		return compWidth + getAddWidth(parentWidth) / getResizableComponents(row);
	}

	private int getAddWidth(int width) {
		return Math.subtractExact(width, prefWidth);		
	}
}
