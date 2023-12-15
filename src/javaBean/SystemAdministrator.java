package javaBean;

public class SystemAdministrator extends User {
	private volatile static SystemAdministrator instance = new SystemAdministrator();

	private SystemAdministrator() {
		this.id = "0";
		this.name = "Admin";
		this.userName = "Admin";
		this.privilege = UserPrivilege.SYSTEM_ADMINISTRATOR;
	}

	public static SystemAdministrator getInstance() {
		if (instance == null) {
			synchronized (SystemAdministrator.class) {
				if (instance == null) {
					instance = new SystemAdministrator();
				}
			}
		}
		return instance;
	}
}
