package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import interfaces.CrudTableListener;

public class CrudTable<T> extends GenericTable<T> {
	
	private JTable table;
	private CrudTableListener<T> listener;
	private int detail, edit, delete;
	
	public CrudTable(Class<?> clss, ArrayList<T> rows) {
		this(clss, rows, false);
	}
	
	public CrudTable(Class<?> clss, ArrayList<T> rows, boolean addIndex) {
		super(clss, rows, addIndex, GenericTableModel.CRUD_TABLE);
		
		detail = getColumnCount() - 3;
		edit = getColumnCount() - 2;
		delete = getColumnCount() -1;
		
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
		addCrudColumns();
		return table;
	}
	
	private void addCrudColumns(){
		
		TableColumn colDetail = table.getColumnModel().getColumn(detail);
		TableColumn colEdit = table.getColumnModel().getColumn(edit);
		TableColumn colDelete = table.getColumnModel().getColumn(delete);

		colDetail.setCellRenderer(GenericTableModel.ICON_DETAIL);
		colEdit.setCellRenderer(GenericTableModel.ICON_EDIT);
		colDelete.setCellRenderer(GenericTableModel.ICON_DELETE);	
	}
	
	private MouseListener getMouseListener(){

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
