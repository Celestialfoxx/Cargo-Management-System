package javaBean;

import java.util.Objects;

public abstract class Employee extends User{
	private String passWord;
	
	protected Employee(String id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	
	protected Employee(String id, String name, String userName) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
	}	
	
	protected Employee(String id, String name, String userName, String passWord) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "Employee [ID=" + id + ", name=" + name + ", userName=" + userName + "]";
	}
	
	
	
	
	
}
