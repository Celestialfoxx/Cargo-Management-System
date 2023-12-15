package layout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBUtil;
import database.DatabaseOperations;
import javaBean.Account;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AdminController implements DatabaseOperations, MenuBarInterface {

	@FXML
	private Button add_button;
	@FXML
	private Button edit_button;
	@FXML
	private Button delete_button;
	@FXML
	private MenuItem op_add;
	@FXML
	private MenuItem op_delete;
	@FXML
	private MenuItem op_edit;
	@FXML
	private TableView<Account> table;
	@FXML
	private TableColumn<Account, Integer> idColumn;
	@FXML
	private TableColumn<Account, String> userNameColumn;
	@FXML
	private TableColumn<Account, String> nameColumn;
	@FXML
	private TableColumn<Account, String> privilegeColumn;
	@FXML
	private TableColumn<Account, String> contactColumn;
	@FXML
	private TableColumn<Account, String> roleColumn;
	@FXML
	private TextField userID_text;
	@FXML
	private TextField userName_text;
	@FXML
	private TextField name_text;
	@FXML
	private TextField role_text;
	@FXML
	private TextField contact_text;
	@FXML
	private TextField password_text;
	@FXML
	private TextField delete_id_text;
	@FXML
	private TextField edit_id_text;
	@FXML
	private TextField edit_text;
	@FXML
	private ChoiceBox<String> edit_choice;
	@FXML
	private ChoiceBox<String> privilege_choice;
	private String choice[] = { "userID", "userName", "name", "role", "contact", "password" };
	private String privilegeChoice[] = { "LOGISTICS_MANAGER", "WAREHOUSE_MANAGER", "GENERAL_STAFF"};
	private ObservableList<String> choiceList = FXCollections.observableArrayList(choice);
	private ObservableList<String> privilegeList = FXCollections.observableArrayList(privilegeChoice);
	private ObservableList<Account> accountList = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		readAll();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
		userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
		privilegeColumn.setCellValueFactory(new PropertyValueFactory<>("privilege"));
		table.setItems(accountList);
		edit_choice.setItems(choiceList);
		privilege_choice.setItems(privilegeList);
	}

	public String readAll() {
		accountList.clear();
		Connection conn = DBUtil.connect();
		if (!viewExists(conn, "user_account")) {
			try (Statement stmt = conn.createStatement();) {
				stmt.execute("CREATE VIEW user_account AS\n"
						+ "SELECT ea.EmployeeID, ea.Username, e.Name, ea.Password, e.Role, e.ContactDetails, ea.Privilege\n"
						+ "       FROM EmployeeAccount ea JOIN Employee e\n"
						+ "           ON ea.EmployeeID == e.EmployeeID;");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		;
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM user_account;")) {
			while (rs.next()) {
				int employeeID = rs.getInt("EmployeeID");
				String userName = rs.getString("UserName");
				String name = rs.getString("Name");
				String role = rs.getString("Role");
				String contact = rs.getString("ContactDetails");
				String password = rs.getString("Password");
				String privilege = rs.getString("Privilege");
				Account account = new Account(employeeID, userName, name, role, contact, password, privilege);
				accountList.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}

	@FXML
	void op_add_event(ActionEvent event) {
		Account account = new Account(Integer.parseInt(userID_text.getText()), userName_text.getText(),
				name_text.getText(), role_text.getText(), contact_text.getText(), password_text.getText(),
				privilege_choice.getValue());
		create(account);
	}

	@FXML
	void edit_event(ActionEvent event) {
		System.out.println(userID_text.getText());
		String editStmt;
		int rowsAffected;
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement();) {
			switch (edit_choice.getValue()) {
			case "userID":
				editStmt = "UPDATE Employee SET EmployeeID = " + edit_text.getText() + " WHERE EmployeeID == "
						+ edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				editStmt = "UPDATE EmployeeAccount SET EmployeeID = " + edit_text.getText() + " WHERE EmployeeID == "
						+ edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			case "userName":
				editStmt = "UPDATE EmployeeAccount SET UserName = " + "\'" + edit_text.getText() + "\'"
						+ " WHERE EmployeeID == " + edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			case "password":
				editStmt = "UPDATE EmployeeAccount SET Password = " + "\'" + edit_text.getText() + "\'"
						+ " WHERE EmployeeID == " + edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			case "name":
				editStmt = "UPDATE Employee SET Name = " + "\'" + edit_text.getText() + "\'" + " WHERE EmployeeID == "
						+ edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			case "role":
				editStmt = "UPDATE Employee SET Role = " + "\'" + edit_text.getText() + "\'" + " WHERE EmployeeID == "
						+ edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			case "contact":
				editStmt = "UPDATE Employee SET ContactDetails = " + "\'" + edit_text.getText() + "\'"
						+ " WHERE EmployeeID == " + edit_id_text.getText() + ";";
				rowsAffected = stmt.executeUpdate(editStmt);
				System.out.println("Rows inserted: " + rowsAffected);
				break;
			}
			initialize();
		} catch (SQLException e) {
			System.out.println("Database error in executeAdd.");
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

	@FXML
	void delete_event(ActionEvent event) {
		System.out.println("aaa");
		System.out.println(userID_text.getText());
		delete(Integer.parseInt(delete_id_text.getText()));
	}

	@Override
	public void create(Object obj) {
		if (obj instanceof Account) {
			Account account = (Account) obj;
			String add_employee = "INSERT INTO Employee (EmployeeID, Name, Role, ContactDetails) VALUES ("
					+ account.getUserID() + "," + "\'" + account.getName() + "\'" + "," + "\'" + account.getRole()
					+ "\'" + "," + "\'" + account.getContact() + "\'" + ");";

			String add_employee_account = "INSERT INTO EmployeeAccount (EmployeeID, Username, Password, Privilege) VALUES ("
					+ account.getUserID() + "," + "\'" + account.getUserName() + "\'" + "," + "\'"
					+ account.getPassWord() + "\'" + "," + "\'" + account.getPrivilege().toString() + "\');";
			System.out.println(add_employee);
			System.out.println(add_employee_account);
			Connection conn = DBUtil.connect();
			try (Statement stmt = conn.createStatement();) {
				int rowsAffected = stmt.executeUpdate(add_employee);
				System.out.println("Rows inserted: " + rowsAffected);
				rowsAffected = stmt.executeUpdate(add_employee_account);
				System.out.println("Rows inserted: " + rowsAffected);
				initialize();
			} catch (SQLException e) {
				System.out.println("Database error in executeAdd.");
				e.printStackTrace();
			} finally {
				DBUtil.closeConnection(conn);
			}
		}

	}

	@Override
	public String read(int id) {

		return null;
	}

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String deleteStmt1 = "DELETE FROM Employee WHERE EmployeeID == " + id + ";";
		String deleteStmt2 = "DELETE FROM EmployeeAccount WHERE EmployeeID == " + id + ";";
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement();) {
			int rowsAffected = stmt.executeUpdate(deleteStmt1);
			System.out.println("Rows inserted: " + rowsAffected);
			rowsAffected = stmt.executeUpdate(deleteStmt2);
			System.out.println("Rows inserted: " + rowsAffected);
			initialize();
		} catch (SQLException e) {
			System.out.println("Database error in executeAdd.");
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

	public static boolean viewExists(Connection conn, String viewName) {
		String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type='view' AND name='" + viewName + "'";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void backToLogin() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		Platform.runLater(() -> {
			// 确保当前窗口已关闭
			try {
				Login app = new Login();
				Stage mainStage = new Stage();
				app.start(mainStage);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void openCargo() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		try {
			Cargo app = new Cargo();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openRepository() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		try {
			Repository app = new Repository();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openLogistics() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		try {
			Logistics app = new Logistics();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openAdmin() {

	}

	@Override
	public void openInvoice() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		try {
			InvoiceInterface app = new InvoiceInterface();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openCustomer() {
		Stage loginStage = (Stage) userID_text.getScene().getWindow();
		loginStage.close();
		// TODO Auto-generated method stub
		try {
			Customer app = new Customer();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
