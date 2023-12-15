package javaBean;

import javafx.scene.control.Alert;

public enum UserPrivilege {
	SYSTEM_ADMINISTRATOR, LOGISTICS_MANAGER, WAREHOUSE_MANAGER, FINANCE_STAFF, CUSTOMER_SERVICE_REPRESENTATIVE,
	GENERAL_STAFF, DRIVER_AND_DELIVERY_PERSONNEL, CUSTOMER;

	public static boolean accessCargo(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER || p == GENERAL_STAFF) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean cargoChange(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean accessRepository(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER || p == GENERAL_STAFF) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean repositoryChange(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean accessInvoice(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER || p == GENERAL_STAFF) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean invoiceChange(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean accessLogistics(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER || p == GENERAL_STAFF) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean logisticsChange(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean accessCustomer(UserPrivilege p) {
		if (p == SYSTEM_ADMINISTRATOR || p == LOGISTICS_MANAGER || p == WAREHOUSE_MANAGER || p == GENERAL_STAFF) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void deny() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("You don't have access!");
		alert.show();
	}
}