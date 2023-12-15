package javaBean;

public class FinanceStuff extends Employee{

	protected FinanceStuff(String id, String name, String userName) {
		super(id, name, userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.FINANCE_STAFF;
	}

}
