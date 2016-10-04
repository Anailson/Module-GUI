package src;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import annotation.SessionForm;
import interfaces.CrudTableListener;
import interfaces.GenericFormListener;
import interfaces.SimpleTableListener;
import model.SideMenu;
import tables.CrudTable;
import tables.SimpleTable;
import view.GenericForm;
import view.SideNavigationMenu;

@SessionForm(title = "Dados do aluno")
public class MainApp {

	private ArrayList<Student> getAlunos() {
		ArrayList<Student> alunos = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			alunos.add(new Student(i * 10, "Aluno #" + i));
		}

		return alunos;
	}

	public SimpleTable<Student> getSimpleTable() {

		SimpleTable<Student> simpleTable = new SimpleTable<>(Student.class, getAlunos());
		simpleTable.addSimpleTableListener(new SimpleTableListener() {
			@Override
			public void detail(int row) {
				System.out.println(simpleTable.detail(row));
			}
		});
		return simpleTable;
	}

	public CrudTable<Student> getCrudTable() {

		CrudTable<Student> crudTable = new CrudTable<>(Student.class, getAlunos(), true);
		crudTable.addCrudTableListener(new CrudTableListener<Student>() {

			@Override
			public void detail(int row) {
				System.out.println("detail " + crudTable.detail(row));
				crudTable.save(new Student(3242, "Kakaroto"));
			}

			@Override
			public void edit(int row, Student obj) {
				obj.setName("Novo Nome");
				crudTable.edit(row, obj);
				System.out.println("edit " + obj);
			}

			@Override
			public void delete(int row, Student obj) {
				System.out.println("delete " + crudTable.delete(row));
			}
		});

		return crudTable;
	}

	public SideNavigationMenu getSideNavMenu() {

		SideNavigationMenu sideNavMenu = new SideNavigationMenu();

		SideMenu menu_1_1 = new SideMenu("Menu #1.1", "Tip about SideMenu #1.1");
		SideMenu menu_1_2 = new SideMenu("Menu #1.2", "Tip about SideMenu #1.2");
		SideMenu menu_1_3 = new SideMenu("Menu #1.3", "Tip about SideMenu #1.3");
		SideMenu menu_1_4 = new SideMenu("Menu #1.4");

		SideMenu menu_2_1 = new SideMenu("Menu #2.1", "Tip SideMenu  #2.1");
		SideMenu menu_3_1 = new SideMenu("Menu #3.1", "Tip SideMenu  #3.1");

		menu_3_1.addMenu(new SideMenu("Menu #4.1"));
		menu_2_1.addMenu(menu_3_1);

		menu_1_1.addMenu(menu_2_1);

		menu_1_1.addMenu(new SideMenu("Menu #2.2"));
		menu_1_1.addMenu(new SideMenu("Menu #2.3"));
		menu_1_1.addMenu(new SideMenu("Menu #2.4", "Tip about SideMenu #4"));

		menu_1_3.addMenu(new SideMenu("Menu #3.1"));

		sideNavMenu.addMenu(menu_1_1);
		sideNavMenu.addMenu(menu_1_2);
		sideNavMenu.addMenu(menu_1_3);
		sideNavMenu.addMenu(menu_1_4);

		return sideNavMenu;
	}

	public JPanel getGenericForm() {

		GenericForm form = new GenericForm(20, Address.class, Student.class);
		
		form.addGenericFormListener(new GenericFormListener() {

			@Override
			public void save(HashMap<String, Object> values) {
				System.out.println("save " + values.get("code"));
				System.out.println("save " + values.get("school"));
				System.out.println("save " + ((boolean[])values.get("sports"))[2]);
				System.out.println("save " + values.get("method"));
			}

			@Override
			public void clear() {
				System.out.println("Clear");
			}

			@Override
			public void cancel() {
				JOptionPane.showMessageDialog(null, "Do you really wants cancel this form?");
			}
			
			@Override
			public void isRequired() {
				JOptionPane.showMessageDialog(null, "Some fields are required!");
			}
		});

		return form;
	}

	public static void main(String args[]) {

		MainApp app = new MainApp();

		JTable table = app.getCrudTable().getTable();
		JPanel sideBar = app.getSideNavMenu().generate();
		long start = System.currentTimeMillis();
		JPanel form = app.getGenericForm();
		System.out.println("Duration " + (System.currentTimeMillis() - start));
		// SemiForm does not resize within a JScrollPane

		JFrame frame = new JFrame("Teste de Menu Flutuante");
		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());

		c.add(form, BorderLayout.NORTH);
		c.add(new JScrollPane(sideBar), BorderLayout.WEST);
		c.add(new JButton("EAST"), BorderLayout.EAST);
		c.add(new JButton("SOUTH"), BorderLayout.SOUTH);
		c.add(new JScrollPane(table), BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
