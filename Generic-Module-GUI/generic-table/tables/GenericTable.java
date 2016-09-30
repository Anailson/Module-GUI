// A hora do Rush, O livro de Eli
package tables;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import annotation.Column;
import column.ObjectColumn;
import genericObject.GenericClass;
import genericObject.GenericField;
import model.GenericTableModel;

public class GenericTable<T> {

	private JTable table;
	private GenericTableModel<T> model;

	protected GenericTable(Class<?> clss, ArrayList<T> rows, boolean addIndex, String typeTable) {

		GenericClass generic = new GenericClass(Column.class,clss);
		ObjectColumn[] columns = generateColumns(generic, clss, addIndex, typeTable);

		model = new GenericTableModel<T>(generic, rows, columns, addIndex, typeTable);
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

	private ObjectColumn[] generateColumns(GenericClass generic, Class<?> clss, boolean addIndex, String typeTable) {

		ObjectColumn[] columns = getColumns(generic);
		columns = addIndexAtColumns(addIndex, columns);
		columns = addCrudColumns(columns, typeTable);

		return columns;
	}

	private ObjectColumn[] addIndexAtColumns(boolean addIndexAtColumns, ObjectColumn[] columns) {

		ObjectColumn[] cols = null;
		if (addIndexAtColumns) {
			cols = new ObjectColumn[columns.length + 1];

			cols[0] = new ObjectColumn("#", "#", 40, false, Integer.class);

			for (int i = 0; i < columns.length; i++) {
				cols[i + 1] = columns[i];
			}
			return cols;
		}
		return columns;
	}

	private ObjectColumn[] addCrudColumns(ObjectColumn[] columns, String typeTable) {
		ObjectColumn[] cols = null;

		if (typeTable == GenericTableModel.CRUD_TABLE) {

			int length = columns.length + 3;
			cols = new ObjectColumn[length];

			for (int i = 0; i < columns.length; i++) {
				cols[i] = columns[i];
			}
			final int DETAIL = length - 3;
			final int EDIT = length - 2;
			final int DELETE = length - 1;

			String detail = GenericTableModel.COL_DETAIL;
			String edit = GenericTableModel.COL_EDIT;
			String delete = GenericTableModel.COL_DELETE;

			cols[DETAIL] = new ObjectColumn(detail, detail, 55, false, ImageIcon.class);
			cols[EDIT] = new ObjectColumn(edit, edit, 55, false, ImageIcon.class);
			cols[DELETE] = new ObjectColumn(delete, delete, 55, false, ImageIcon.class);

			return cols;
		}
		return columns;
	}

	private void setColumnsWidth(ObjectColumn[] columns) {

		for (int i = 0; i < columns.length; i++) {

			ObjectColumn column = columns[i];

			if (column.getWidth() != ObjectColumn.MIN_WIDTH) {

				TableColumn col = table.getColumnModel().getColumn(i);
				col.setMaxWidth(column.getWidth());
				col.setMinWidth(column.getWidth());
				// col.setCellRenderer(GenericTableModel.DEFAULT);
			}
		}
	}
	
	private ObjectColumn[] getColumns(GenericClass generic){
		
		ArrayList<GenericField> fields = generic.getGenericFields();
		ObjectColumn [] columns = new ObjectColumn[fields.size()];
		
		for (int i = 0; i < fields.size(); i++) {
			GenericField field = fields.get(i);
			Column colum = (Column) field.getAnnotation();
			
			columns[i] = new ObjectColumn(field.getFieldName(), colum.title(), colum.width(), colum.editable(),
					field.getFieldType());
		}
		return columns;
	}
}