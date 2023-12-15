package javaBean;

public class LogisticsManager extends Employee {

	public LogisticsManager(String id, String userName) {
		super(id, userName);
		this.privilege = UserPrivilege.LOGISTICS_MANAGER;
		
	}
	
	public LogisticsManager(String iD, String name, String userName) {
		super(iD, name, userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.LOGISTICS_MANAGER;
		
	}
}




