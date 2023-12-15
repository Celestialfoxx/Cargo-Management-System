package javaBean;

public class WarehouseManager extends Employee {

	public WarehouseManager(String id, String userName) {
		super(id,userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.WAREHOUSE_MANAGER;
	}
	public WarehouseManager(String id, String name, String userName) {
		super(id, name, userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.WAREHOUSE_MANAGER;
	}
	
}
