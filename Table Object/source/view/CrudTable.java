package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import interfaces.CrudTableListener;

public class CrudTable<T> extends GenericTable<T> {
	
	private JTable table;
	private CrudTableListener<T> listener;
	
	public CrudTable(ArrayList<T> rows, Column[] columns) {
		this(rows, columns, false);
	}
	
	public CrudTable(ArrayList<T> rows, Column[] columns, boolean addIndex) {
		super(rows, addCrudColumns(columns), addIndex, GenericTableModel.CRUD_TABLE);
		
		listener = new CrudTableListener<T>() {
			
			@Override
			public void detail(int row) {}
			
			@Override
			public void edit(int row, T obj) {}
			
			@Override
			public void delete(int row, T obj) {}
		};		
	}
	
	public void addCrudTableListener(CrudTableListener<T> listener) {
		this.listener = listener;
	}
	
	@Override
	public JTable getTable() {
		table = super.getTable();
		table.setRowHeight(35);
		table.addMouseListener(getMouseListener());
		return table;
	}
	
	public T detail(int row){
		return super.detail(row);
	}
	
	public T edit(int row, T obj){
		return super.edit(row, obj);
	}
	
	public T delete(int row){
		return super.delete(row);
	}
	
	private static Column[] addCrudColumns(Column[] columns){
		int length = columns.length + 3;
		Column[] cols = new Column[length];

		for (int i = 0; i < columns.length; i++) {
			cols[i] = columns[i];
		}
		final int DETAIL = length - 3;
		final int EDIT = length - 2;
		final int DELETE = length - 1;

		cols[DETAIL] = new Column(GenericTableModel.COL_DETAIL, 60, true, ImageIcon.class);
		cols[EDIT] = new Column(GenericTableModel.COL_EDIT, 60, true, ImageIcon.class);
		cols[DELETE] = new Column(GenericTableModel.COL_DELETE, 60, true, ImageIcon.class);
		return cols;
	}
	
	private MouseListener getMouseListener(){
		
		int length = getColumnCount();
		int detail = length - 3;
		int edit = length - 2;
		int delete = length - 1;

		TableColumn colDetail = table.getColumnModel().getColumn(detail);
		TableColumn colEdit = table.getColumnModel().getColumn(edit);
		TableColumn colDelete = table.getColumnModel().getColumn(delete);

		colDetail.setCellRenderer(GenericTableModel.ICON_DETAIL);
		colEdit.setCellRenderer(GenericTableModel.ICON_EDIT);
		colDelete.setCellRenderer(GenericTableModel.ICON_DELETE);
		
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
			
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				T obj = detail(row);
				
				if(column == detail){

					listener.detail(row);
					
				} else if(column == edit){
					
					listener.edit(row, obj);
					
				} else if(column == delete){
					listener.delete(row, obj);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		};
	}
}
