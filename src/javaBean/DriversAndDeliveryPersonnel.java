package javaBean;

public class DriversAndDeliveryPersonnel extends Employee{

	protected DriversAndDeliveryPersonnel(String id, String name, String userName) {
		super(id, name, userName);
		// TODO Auto-generated constructor stub
		this.privilege = UserPrivilege.DRIVER_AND_DELIVERY_PERSONNEL;
	}

}
