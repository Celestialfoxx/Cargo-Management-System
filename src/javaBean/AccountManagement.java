package javaBean;

public interface AccountManagement {
	String createAccount();
	String deleteAccount();
	String resetAccount();
	String changePrivilege(User user, UserPrivilege privilege);
	
}
