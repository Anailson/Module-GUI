package model;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import column.ObjectColumn;
import genericObject.GenericObject;

@SuppressWarnings("serial")
public class GenericTableModel<T> extends AbstractTableModel {

	private static final String URL_ICON_DETAIL = "/icons/icon_detail.png";
	private static final String URL_ICON_EDIT = "/icons/icon_edit.png";
	private static final String URL_ICON_DELETE = "/icons/icon_delete.png";

	public static final String CRUD_TABLE = "crud";
	public static final String SIMPLE_TABLE = "simple";
	public static final String COL_DETAIL = "Detail";
	public static final String COL_EDIT = "Edit";
	public static final String COL_DELETE = "Delete";

	public static final IconCellRenderer DEFAULT = new IconCellRenderer();
	public static final IconCellRenderer ICON_DETAIL = new IconCellRenderer(COL_DETAIL);
	public static final IconCellRenderer ICON_EDIT = new IconCellRenderer(COL_EDIT);
	public static final IconCellRenderer ICON_DELETE = new IconCellRenderer(COL_DELETE);

	private GenericObject generic;
	private ObjectColumn[] columns;
	private ArrayList<T> rows;
	private boolean addIndex;
	private String typeTable;

	public GenericTableModel(GenericObject generic, ArrayList<T> rows, ObjectColumn[] columns, boolean addIndex, String typTable) {

		this.generic = generic;
		this.rows = rows;
		this.columns = columns;
		this.addIndex = addIndex;
		this.typeTable = typTable;
	}

	protected ObjectColumn getColumn(int column) {
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
			Object obj = makeCrudColumns(column, columns.length);
			if (obj != null) {
				return obj;
			}
		}
		
		String className = generic.getChildClass().getCanonicalName();
		String methodName = generic.getGetterName(columns[column].getField());

		try {

			Class<?> cls = Class.forName(className);
			Object obj = rows.get(row);
			Method method = cls.getDeclaredMethod(methodName);
			return method.invoke(obj);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (NoSuchMethodException | NullPointerException e) {

			throw new IllegalStateException("Are you sure that field '" + columns[column].getField() + "' exists at "
					+ className + " class?\n" + "The column names must match the class fields.");

		} catch (IllegalAccessException | InvocationTargetException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {

		String className = generic.getChildClass().getCanonicalName();
		String methodName = generic.getSetterName(columns[column].getField());

		try {

			Class<?> cls = (Class<?>) Class.forName(className);
			Object obj = rows.get(row);
			Method method = cls.getMethod(methodName, columns[column].getDefaultType());
			method.invoke(obj, value);

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
	public Class<?> getColumnClass(int columnIndex) {

		if (addIndex && columnIndex == 0) {
			// Column #(index) it's Integer type
			return Integer.class; 
		}

		if (typeTable == CRUD_TABLE && columnIndex > getColumnCount() - 4) {
			// Columns detail, edit & delete are ImageIcon type
			return ImageIcon.class; 
		}

		ObjectColumn column = getColumn(columnIndex);

		if (generic.getFieldType(column.getField()) != column.getDefaultType()) {
			throw new IllegalStateException("Column type " + column.getField() + " must match as field type.");
		}
		return getColumn(columnIndex).getClassType();		
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

	private Object makeCrudColumns(int column, int length) {

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

	private static class IconCellRenderer extends DefaultTableCellRenderer {

		private String icon;

		public IconCellRenderer() {
			icon = "bool";
		}

		public IconCellRenderer(String icon) {
			this.icon = icon;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			setHorizontalAlignment(CENTER);

			if (value instanceof Integer) {

				setHorizontalAlignment(RIGHT);

			} else if (value instanceof Boolean) {

				JCheckBox checkBox = new JCheckBox();
				checkBox.setSelected(((Boolean) value).booleanValue());
				checkBox.setHorizontalAlignment(CENTER);
				checkBox.setOpaque(true);
				checkBox.setBackground(Color.WHITE);

				return checkBox;

			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

		protected void setValue(Object value) {

			switch (icon) {
			case COL_DETAIL:

				setIcon(new ImageIcon(getClass().getResource(URL_ICON_DETAIL)));
				break;

			case COL_EDIT:

				setIcon(new ImageIcon(getClass().getResource(URL_ICON_EDIT)));
				break;

			case COL_DELETE:

				setIcon(new ImageIcon(getClass().getResource(URL_ICON_DELETE)));
				break;
			default:
				super.setValue(value);
				break;
			}
		}
	}
}
