package javaBean;

public class Account {
	private int userID;
	private String userName;
	private String name;
	private String role;
	private String contact;
	private String passWord;
	private String privilege;

	public Account(int userID, String userName, String name, String role, String contact, String passWord,
			String privilege) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.name = name;
		this.role = role;
		this.contact = contact;
		this.passWord = passWord;
		this.privilege = privilege;
	}

	public Account(int userID, String userName, String name, String role, String contact) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.name = name;
		this.role = role;
		this.contact = contact;
	}

	public Account(int userID, String userName, String name, String role, String contact, String passWord) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.name = name;
		this.role = role;
		this.contact = contact;
		this.passWord = passWord;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
