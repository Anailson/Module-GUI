package layout;

public class HorizontalLayoutConstraint {

	private int row;
	private boolean resizable;
	private boolean required;

	public HorizontalLayoutConstraint(int row) {
		this(row, false, false);
	}

	public HorizontalLayoutConstraint(int row,  boolean required, boolean resizable) {

		this.row = row;
		this.required = required;
		this.resizable = resizable;
	}

	public int getRow() {
		return row;
	}

	public boolean isResizable() {
		return resizable;
	}

	public boolean isRequired() {
		return required;
	}
}