import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.AbstractTable;
import model.Column;

public class App {

	@SuppressWarnings("serial")
	public static class AbstractTableEx extends AbstractTable<Object> {

		public AbstractTableEx(ArrayList<Object> rows, Column[] columns) {
			super(Column.class, rows, columns);
		}

		/*
		@Override
		public Object getCell(Object element, int column) {
			switch (column) {
			case 0:
				return 0;

			case 1:
				return 1;
			case 2:
				return 2;
			}
			return "vish";
		}

		@Override
		public void setCell(Object o, Object e, int column) {
			switch (column) {
			case 0:
				e = (int) o;
				break;
			case 1:
				e = "Inteiro";
				break;
			}
		}
		*/
	}

	public static void main(String[] args) {

		new App();
		
		Column[] colunas = new Column[] { new Column("Coluna #0"), new Column("Coluna #1", Integer.class, true),
				new Column("Coluna #2", true) };
		ArrayList<Object> strings = new ArrayList<>();
		strings.add(1);
		strings.add("tudo bem");
		strings.add(2);
		strings.add("tudo bem");
		strings.add(3);
		strings.add("tudo bem");
		strings.add(4);
		strings.add("tudo bem");
		strings.add(5);
		strings.add("tudo bem");
		strings.add(6);
		strings.add("tudo bem");
		strings.add(7);
		strings.add("tudo bem");
		strings.add(8);
		strings.add("tudo bem");
		strings.add(9);
		strings.add("tudo bem");
		strings.add(10);
		strings.add("tudo bem");
		strings.add(11);
		strings.add("tudo bem");
		strings.add(12);
		strings.add("tudo bem");
		strings.add(13);
		strings.add("tudo bem");
		strings.add(14);
		strings.add("tudo bem");
		strings.add(15);
		strings.add("tudo bem");
		strings.add(16);
		strings.add("tudo bem");

		AbstractTableEx model = new AbstractTableEx(strings, colunas);
		JTable table = new JTable(model);
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(table);

		JFrame frame = new JFrame("Teste de Menu Flutuante");

		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());

		c.add(new JButton("NORTH"), BorderLayout.NORTH);
		c.add(new JButton("WEST"), BorderLayout.WEST);
		c.add(new JButton("EAST"), BorderLayout.EAST);
		c.add(new JButton("SOUTH"), BorderLayout.SOUTH);
		c.add(pane, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
