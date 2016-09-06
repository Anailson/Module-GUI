package view;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class GenericTable<T>{

	private JTable table;
	private GenericTableModel<T> model;
	private ArrayList<T> rows;
	private Column[] columns;
	private boolean addIndexAtColumns;
	
	@SuppressWarnings("unused")
	private GenericTable() {}
	
	protected GenericTable(ArrayList<T> rows, Column[] columns, boolean addIndex, String typeTable) {
		this.rows = rows;
		this.columns = columns;
		this.addIndexAtColumns = addIndex;
		
		validateObject();

		model = new GenericTableModel<T>(this.rows, this.columns, addIndexAtColumns, typeTable);
		table = new JTable(model);		
		table.getTableHeader().setReorderingAllowed(false);
	}
	
	protected int getColumnCount(){
		return model.getColumnCount();
	}
	
	protected JTable getTable() {
		setColumnsWidth();
		return table;
	}
	
	protected T detail(int row){
		return model.detail(row);
	}
	
	protected ArrayList<T> detail(int firstIndex, int lastIndex){
		return model.detail(firstIndex, lastIndex);
	}
	
	protected ArrayList<T> detailAll(){
		return model.detailAll();
	}
	
	protected T edit(int row, T obj){
		return model.edit(row, obj);
	}
	
	protected T delete(int row){
		return model.delete(row);
	}

	protected ArrayList<T> delete(int firstIndex, int lastIndex){
		return model.delete(firstIndex, lastIndex);
	}
	
	protected ArrayList<T> deleteAll(){
		return model.deleteAll();
	}
	
	private void validateObject() {
		if (rows.size() > 0) {
			Class<?> subClass = rows.get(0).getClass();
			Class<?> superClass = TableObject.class;

			if (!subClass.getSuperclass().equals(superClass)) {
				throw new IllegalStateException("Class " + subClass.getName() + " must extends "
						+ superClass.getSimpleName() + ".");
			}
			addIndexColumn();
		}
	}
	
	private void addIndexColumn() {
		
		if (addIndexAtColumns) {
			Column[] cols = new Column[columns.length + 1];
			
			cols[0] = new Column("#", "#", 40, false, Integer.class);
			
			for (int i = 0; i < columns.length; i++) {
				cols[i + 1] = columns[i];
			}
			columns = cols;
		}
	}
	
	private void setColumnsWidth() {
		
		for (int i = 0; i < columns.length; i++) {

			Column column = columns[i];

			if (column.getWidth() != Column.MIN_WIDTH) {
				
				TableColumn col = table.getColumnModel().getColumn(i);
				col.setMaxWidth(column.getWidth());
				col.setMinWidth(column.getWidth());
			}
		}
	}
}
