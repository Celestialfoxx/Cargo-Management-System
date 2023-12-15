package javaBean;

import java.util.Objects;

public class CustomerAccount extends User {
	private String address;

	public CustomerAccount(String id, String name, String email, String address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
	}

	protected CustomerAccount(String id, String name, String address, String phoneNumber, String email) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.privilege = UserPrivilege.CUSTOMER;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, address, name, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAccount other = (CustomerAccount) obj;
		return Objects.equals(id, other.id) && Objects.equals(address, other.address)
				&& Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "Customer [ID=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}

}
