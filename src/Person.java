
import java.io.Serializable;


abstract public class Person implements Serializable{
	
	private String name;
	private String address;
	
	public Person(String newName, String newAddress){
		this.name = newName;
		this.address = newAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
