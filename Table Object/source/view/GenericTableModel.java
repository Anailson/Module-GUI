package view;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class GenericTableModel<T> extends AbstractTableModel {

	private static final String GET = "get";
	private static final String SET = "set";
	private static final String IS = "is";
	private static final String URL_ICON_DETAIL = "icons/icon_detail.png";
	private static final String URL_ICON_EDIT = "icons/icon_edit.png";
	private static final String URL_ICON_DELETE = "icons/icon_delete.png";

	protected static final String CRUD_TABLE = "crud";
	protected static final String SIMPLE_TABLE = "simple";
	protected static final String COL_DETAIL = "Details";
	protected static final String COL_EDIT = "Edit";
	protected static final String COL_DELETE = "Delete";

	protected static final IconCellRenderer ICON_DETAIL = new IconCellRenderer(COL_DETAIL);
	protected static final IconCellRenderer ICON_EDIT = new IconCellRenderer(COL_EDIT);
	protected static final IconCellRenderer ICON_DELETE = new IconCellRenderer(COL_DELETE);

	private Column[] columns;
	private ArrayList<T> rows;
	private boolean addIndex;
	private String typeTable;

	protected GenericTableModel(ArrayList<T> rows, Column[] columns, boolean addIndex, String typTable) {

		this.rows = rows;
		this.columns = columns;
		this.addIndex = addIndex;
		this.typeTable = typTable;
	}

	private Column getColumn(int column) {
		return columns[column];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int row, int column) {

		if (addIndex && column == 0) {
			return row + 1;
		}

		if (typeTable == CRUD_TABLE && column > columns.length - 4) {
			Object obj = crudColumns(column, columns.length);
			if (obj != null) {
				return obj;
			}
		}

		T element = rows.get(row);
		String className = element.getClass().getCanonicalName();
		String methodName = getterName(columns[column]);

		try {

			Class<?> cls = Class.forName(className);

			Method method = cls.getDeclaredMethod(methodName);
			return method.invoke(element);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (NoSuchMethodException e) {

			throw new IllegalStateException("Are you sure that field '" + columns[column].getField() + "' exists at "
					+ className + " class?\n" + "The column names must match the class fields.");

		} catch (IllegalAccessException | InvocationTargetException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {

		String methodName = setterName(columns[column]);
		T element = rows.get(row);
		String className = element.getClass().getCanonicalName();
		
		try {
			
			Class<?> cls = (Class<?>) Class.forName(className);
			Method method = cls.getMethod(methodName, columns[column].getDefaultType());
			method.invoke(element, value);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (NoSuchMethodException | SecurityException e) {

			throw new IllegalStateException("Are you sure that field '" + columns[column].getField() + "' exists at "
					+ className + " class?\n" + "The column names must match the class fields.");

		} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {

			e.printStackTrace();
		}
		fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int column) {
		return getColumn(column).getTitle();
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return getColumn(column).getClassType();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int column) {
		return getColumn(column).isEditable();
	}

	public boolean save(T e) {
		boolean flag = rows.add(e);
		if (flag) {
			int rowIndex = rows.size() - 1;
			fireTableRowsInserted(rowIndex, rowIndex);
		}
		return flag;
	}

	public boolean save(ArrayList<T> values) {
		boolean flag = rows.addAll(values);
		if (flag) {
			int lastRow = getRowCount();
			fireTableRowsInserted(lastRow, getRowCount() - 1);
		}
		return flag;
	}

	public T edit(int row, T e) {
		T element = rows.set(row, e);
		fireTableRowsUpdated(row, row);
		return element;
	}

	public T delete(int row) {
		T e = rows.remove(row);
		fireTableRowsDeleted(row, row);
		return e;
	}

	public ArrayList<T> delete(int firstRow, int lastRow) {

		ArrayList<T> deleted = new ArrayList<>();

		for (int i = lastRow; i >= firstRow; i--) {
			deleted.add(rows.remove(i));
		}
		fireTableRowsDeleted(firstRow, lastRow);
		return deleted;
	}

	public ArrayList<T> deleteAll() {
		return delete(0, getRowCount() - 1);
	}

	public T detail(int index) {
		return rows.get(index);
	}

	public ArrayList<T> detail(int firstIndex, int lastIndex) {

		ArrayList<T> details = new ArrayList<>();

		for (int i = firstIndex; i < lastIndex; i++) {
			details.add(rows.get(i));
		}

		return details;
	}

	public ArrayList<T> detailAll() {
		return detail(0, getRowCount() - 1);
	}

	private String getterName(Column column) {
		if (column.getDefaultType() == Boolean.class || column.getDefaultType() == boolean.class) {
			return IS + methodName(column.getField());
		}
		return GET + methodName(column.getField());
	}

	private String setterName(Column column) {
		return SET + methodName(column.getField());
	}

	private String methodName(String field) {

		char oldChar = field.charAt(0);
		char newChar = field.substring(0, 1).toUpperCase().charAt(0);

		return field.replace(oldChar, newChar);
	}

	private Object crudColumns(int column, int length) {

		final int detail = columns.length - 3;
		final int edit = columns.length - 2;
		final int delete = columns.length - 1;

		if (column == detail) {

			return ICON_DETAIL;

		} else if (column == edit) {

			return ICON_EDIT;

		} else if (column == delete) {

			return ICON_DELETE;
		}

		return null;
	}

	private static class IconCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

		private String icon;

		public IconCellRenderer(String icon) {
			this.icon = icon;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			int i = row % 2;
			if (i == 0) {
				setBackground(Color.LIGHT_GRAY);
			} else {
				setBackground(Color.WHITE);
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

		protected void setValue(Object value) {

			setHorizontalAlignment(CENTER);

			switch (icon) {
			case COL_DETAIL:
				setIcon(new ImageIcon(URL_ICON_DETAIL));
				break;
			case COL_EDIT:
				setIcon(new ImageIcon(URL_ICON_EDIT));
				break;
			case COL_DELETE:
				setIcon(new ImageIcon(URL_ICON_DELETE));
				break;

			default:
				super.setValue(value);
				System.out.println("Default: " + value);
				break;
			}
		}
	}
}
