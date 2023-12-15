package layout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBUtil;
import javaBean.CurrentUser;
import javaBean.GeneralStaff;
import javaBean.LogisticsManager;
import javaBean.SystemAdministrator;
import javaBean.UserPrivilege;
import javaBean.WarehouseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button btnShowNumber;

	@FXML
	private PasswordField password;

	@FXML
	private TextField username;

	@FXML
	public void clickLogin() {
		String userName = username.getText().strip();
		String passWord = password.getText().strip();
		System.out.println("Login is clicked");
		System.out.println(userName);
		System.out.println(passWord);
		// System.out.println(userExist());

		if (isAdmin(userName, passWord)) {
			Stage loginStage = (Stage) username.getScene().getWindow();
			loginStage.close();

			try {
				Admin adminApp = new Admin();
				Stage mainStage = new Stage();
				adminApp.start(mainStage);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!userExist()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("User not existed");
			alert.show();
			return;
		} 
		
		if (allInfoCorrect()) {
			// stage.hide()
			// 关闭当前stage，打开操作stage
			Stage loginStage = (Stage) username.getScene().getWindow();
			loginStage.close();

			try {
				Repository mainApp = new Repository();
				Stage mainStage = new Stage();
				mainApp.start(mainStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Password is wrong");
			alert.show();
		}
	}

	boolean userExist() {
		String sql = "select count (*) from EmployeeAccount where username = '" + username.getText() + "'";
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:src/database/identifier.sqlite");
			c.setAutoCommit(false);
			System.out.println("UE: Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.getInt(1) == 1) {
				rs.close();
				stmt.close();
				c.close();
				System.out.println("User Exist: Operation done successfully -- result:true");
				return true;
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully -- result:false");
		return false;
	}

	boolean allInfoCorrect() {
		String sql = "select * from EmployeeAccount where username = '" + username.getText() + "' and password = '"
				+ password.getText() + "'";
		System.out.println(sql);
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			//System.out.println("AI: Opened database successfully");
			UserPrivilege.LOGISTICS_MANAGER.toString();
			assert(rs.next() == true);
			if (rs.next()) {
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				switch (rs.getString("Privilege")) {
				case "LOGISTICS_MANAGER":
					CurrentUser.setUser(
							new LogisticsManager(String.valueOf(rs.getInt("EmployeeID")), rs.getString("Username")));
					System.out.println(CurrentUser.getUser().getPrivilege());
					break;
				case "WAREHOUSE_MANAGER":
					CurrentUser.setUser(
							new WarehouseManager(String.valueOf(rs.getInt("EmployeeID")), rs.getString("Username")));
					break;
				case "GENERAL_STAFF":
					CurrentUser.setUser(
							new GeneralStaff(String.valueOf(rs.getInt("EmployeeID")), rs.getString("Username")));
					break;
				}
				return true;
			}
			
			

		} catch (

		Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully -- result:false");
		return false;
	}

	@FXML
	public void keyEvent() {
		btnShowNumber.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case ENTER:
				clickLogin();
				break;
			default:
				break;
			}
		});
	}

	boolean isAdmin(String username, String password) {
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from AdminAccount where AdminUsername = '" + username
						+ "' and AdminPassword = '" + password + "'");) {

			if (rs.next()) {
				CurrentUser.setUser(SystemAdministrator.getInstance());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}

		return false;
	}
}
