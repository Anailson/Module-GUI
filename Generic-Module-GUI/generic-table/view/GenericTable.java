// A hora do Rush, O livro de Eli
package view;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class GenericTable<T> {

	private JTable table;
	private GenericTableModel<T> model;
	private GenericObject generic;
	private boolean addIndexAtColumns;

	protected GenericTable(Class<?> clss, ArrayList<T> rows, boolean addIndex, String typeTable) {

		this.generic = new GenericObject(clss);
		this.addIndexAtColumns = addIndex;
		Column[] columns = generateColumns(clss, typeTable);

		model = new GenericTableModel<T>(generic, rows, columns, addIndexAtColumns, typeTable);
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);

		setColumnsWidth(columns);
	}

	protected int getColumnCount() {
		return model.getColumnCount();
	}

	protected JTable getTable() {
		return table;
	}

	public boolean save(T obj) {
		return model.save(obj);
	}

	public boolean save(ArrayList<T> objs) {
		return model.save(objs);
	}

	public T detail(int row) {
		return model.detail(row);
	}

	public ArrayList<T> detail(int firstIndex, int lastIndex) {
		return model.detail(firstIndex, lastIndex);
	}

	public ArrayList<T> detailAll() {
		return model.detailAll();
	}

	public T delete(int row) {
		return model.delete(row);
	}

	public T edit(int row, T obj) {
		return model.edit(row, obj);
	}

	public ArrayList<T> delete(int firstIndex, int lastIndex) {
		return model.delete(firstIndex, lastIndex);
	}

	public ArrayList<T> deleteAll() {
		return model.deleteAll();
	}

	private Column[] generateColumns(Class<?> clss, String typeTable) {

		Column[] columns = generic.getColumns();
		columns = addIndexAtColumns(columns);
		columns = addCrudColumns(columns, typeTable);

		return columns;
	}

	private Column[] addIndexAtColumns(Column[] columns) {

		Column[] cols = null;
		if (addIndexAtColumns) {
			cols = new Column[columns.length + 1];

			cols[0] = new Column("#", "#", 40, false, Integer.class);

			for (int i = 0; i < columns.length; i++) {
				cols[i + 1] = columns[i];
			}
			return cols;
		}
		return columns;
	}

	private Column[] addCrudColumns(Column[] columns, String typeTable) {
		Column[] cols = null;

		if (typeTable == GenericTableModel.CRUD_TABLE) {

			int length = columns.length + 3;
			cols = new Column[length];

			for (int i = 0; i < columns.length; i++) {
				cols[i] = columns[i];
			}
			final int DETAIL = length - 3;
			final int EDIT = length - 2;
			final int DELETE = length - 1;

			String detail = GenericTableModel.COL_DETAIL;
			String edit = GenericTableModel.COL_EDIT;
			String delete = GenericTableModel.COL_DELETE;

			cols[DETAIL] = new Column(detail, detail, 55, false, ImageIcon.class);
			cols[EDIT] = new Column(edit, edit, 55, false, ImageIcon.class);
			cols[DELETE] = new Column(delete, delete, 55, false, ImageIcon.class);

			return cols;
		}
		return columns;
	}

	private void setColumnsWidth(Column[] columns) {

		for (int i = 0; i < columns.length; i++) {

			Column column = columns[i];

			if (column.getWidth() != Column.MIN_WIDTH) {

				TableColumn col = table.getColumnModel().getColumn(i);
				col.setMaxWidth(column.getWidth());
				col.setMinWidth(column.getWidth());
				// col.setCellRenderer(GenericTableModel.DEFAULT);
			}
		}
	}
}
