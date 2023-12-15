package javaBean;

public class CustomerServiceRepresentative extends Employee{

	protected CustomerServiceRepresentative(String id, String name, String userName) {
		super(id, name, userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.CUSTOMER_SERVICE_REPRESENTATIVE;
	}
	

}
