package controller;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Column;

@SuppressWarnings("serial")
public class AbstractTable<T> extends AbstractTableModel {

	private Column[] columns;
	private ArrayList<T> rows;
	private Class<?> cls;

	// TODO create a class Settings(?) save all settings (like a addIndex)
	boolean addIndex = true;

	public AbstractTable(Class<?> cls, ArrayList<T> rows, Column[] columns) {
		this.cls = cls;
		this.rows = rows;
		this.columns = columns;

		addIndexAtColumns(columns);
	}

	private void addIndexAtColumns(Column[] cols) {

		if (addIndex) {

			columns = new Column[cols.length + 1];
			columns[0] = new Column("#", Integer.class);

			for (int i = 0; i < cols.length; i++) {
				columns[i + 1] = cols[i];
			}

		} else {
			columns = cols;
		}
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

		return null;

	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		// TODO What I have to do to make that cell to be updated?
		//setCell(value, get(row), column);
		//fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int column) {
		return getColumn(column).getTitle();
	}

	public Class<?> getColumnClass(int column) {
		return getColumn(column).getType();
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

	public T update(int row, T e) {
		T element = rows.get(row);
		fireTableRowsUpdated(row, row);
		return element;
	}

	public T remove(int row) {
		T e = rows.remove(row);
		fireTableRowsDeleted(row, row);
		return e;
	}

	public T get(int row) {
		return rows.get(row);
	}

	public ArrayList<T> get() {
		return rows;
	}
}
