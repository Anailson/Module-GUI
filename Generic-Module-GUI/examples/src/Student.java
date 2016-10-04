package src;

import annotation.*;

@SessionForm(title = "Dados do aluno")
public class Student {

	@Column(width = 70)
	@RowElement(index = 0, required = true)
	@RowElementSettings(minWidth = 50, typeClass = "JSpinner", title = "Code")
	private int code;

	@Column(title = "Name of student")
	@RowElement(index = 0, required = true, resizable = true)
	@RowElementSettings(minWidth = 250, title = "Name of student", values = "Nome do aluno")
	private String name;

	@Column(width = 70)
	@RowElement(index = 1, required = true)
	@RowElementSettings(typeClass = "JComboBox", title = "Situação da escola", values = { "Publica",
			"Privada", "Mista"})
	private String school; //

	@Column(title = "Play any sport")
	@RowElement(index = 1, required = true)
	@RowElementSettings(typeClass = "JCheckBox", title = "Pratica algum esporte?", values = { "Volei",
			"Futebol", "Basquete", "Outro"})
	private String[] sports;

	@Column(title = "Incrição", editable = true, width = 50)
	@RowElement(index = 2, required = true)
	@RowElementSettings(typeClass = "JRadioButton", title = "Incrição", values = { "ENEM", "Vestibular" })
	private int method; //

	@Column(title = "Is active?")
	@RowElement(index = 0, required = true)
	@RowElementSettings(typeClass = "JCheckBox", values = "Ativo?")
	private boolean active; // JCheckBox

	@RowElement(index = 3, resizable = true)
	private Address address;

	public Student() {
		this(0, "-");
	}

	public Student(int code, String name) {

		this.code = code;
		this.name = name;
		this.school = "-";
		this.sports = new String[] {};
		this.method = -1;
		this.active = false;
		this.address = new Address();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String[] getSports() {
		return sports;
	}

	public void setSports(String[] sports) {
		this.sports = sports;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
