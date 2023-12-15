package javaBean;

public class GeneralStaff extends Employee {

	public GeneralStaff(String id, String userName) {
		super(id, userName);
		this.privilege = UserPrivilege.GENERAL_STAFF;
	}

	public GeneralStaff(String id, String name, String userName) {
		super(id, name, userName);
		this.privilege = UserPrivilege.GENERAL_STAFF;
	}

}
