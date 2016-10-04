package src;
import annotation.SessionForm;
import annotation.RowElement;
import annotation.RowElementSettings;

@SessionForm (title = "Address")
public class Address {

	@RowElement(index = 0, required = false, resizable = true)
	@RowElementSettings(minWidth = 150, title = "Street", typeClass = "", values = "")
	private String street;
	
	@RowElement(index = 0, required = false, resizable = false)
	@RowElementSettings(minWidth = 0, title = "Number", typeClass = "JSpinner", values = "")
	private int number;

	public Address() {
		this(100);
		street = "";
	}

	public Address(int nuemro) {
		this.street = "rua";
		this.number = nuemro;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "[Rua=" + street + ", Numero=" + number + "]";
	}
}
