package javaBean;

public abstract class User {
	protected String id;
	protected String name;
	protected String userName;
	protected String phoneNumber;
	protected String email;
	protected UserPrivilege privilege;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserPrivilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(UserPrivilege privilege) {
		this.privilege = privilege;
	}
	
	
}
