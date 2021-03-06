package tables;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;

import interfaces.SimpleTableListener;
import model.GenericTableModel;

public class SimpleTable <T> extends GenericTable<T>{
	
	private SimpleTableListener listener;
	private JTable table;
	
	public SimpleTable(Class<?> clss, ArrayList<T> rows) {
		this(clss, rows, false);
	}
	
	public SimpleTable(Class<?> clss, ArrayList<T> rows, boolean addIndex) {
		super(clss, rows, addIndex, GenericTableModel.SIMPLE_TABLE);
		
		listener = new SimpleTableListener() {	
			@Override
			public void detail(int row) {}
		};
	}
	
	public void addSimpleTableListener(SimpleTableListener listener){
		this.listener = listener;
	}
	
	public JTable getTable() {
		
		table = super.getTable();		
		table.addMouseListener(getMouseListener());

		return table;
	}
	
	public T detail(int row){
		return super.detail(row);
	}
		
	private MouseListener getMouseListener(){
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
			
				listener.detail(table.getSelectedRow());
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
