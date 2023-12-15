package layout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBUtil;
import database.DatabaseOperations;
import javaBean.CurrentUser;
import javaBean.CustomerAccount;
import javaBean.UserPrivilege;
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

public class CustomerController implements DatabaseOperations, MenuBarInterface {

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
	private TableView<CustomerAccount> table;
	@FXML
	private TableColumn<CustomerAccount, Integer> idColumn;
	@FXML
	private TableColumn<CustomerAccount, String> nameColumn;
	@FXML
	private TableColumn<CustomerAccount, String> addressColumn;
	@FXML
	private TableColumn<CustomerAccount, String> contactColumn;
	@FXML
	private TextField customerID_text;
	@FXML
	private TextField name_text;
	@FXML
	private TextField contact_text;
	@FXML
	private TextField address_text;
	@FXML
	private TextField delete_id_text;
	@FXML
	private TextField edit_id_text;
	@FXML
	private TextField edit_text;
	@FXML
	private ChoiceBox<String> edit_choice;
	private String choice[] = { "CustomerID", "Name", "ContactDetails", "Address" };
	private ObservableList<String> choiceList = FXCollections.observableArrayList(choice);
	private ObservableList<CustomerAccount> accountList = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		readAll();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		contactColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		table.setItems(accountList);
		edit_choice.setItems(choiceList);
	}

	public String readAll() {
		accountList.clear();
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customer;")) {
			while (rs.next()) {
				int customerID = rs.getInt("CustomerID");
				String name = rs.getString("Name");
				String contact = rs.getString("ContactDetails");
				String address = rs.getString("Address");
				CustomerAccount account = new CustomerAccount(String.valueOf(customerID), name, contact, address);
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
		CustomerAccount account = new CustomerAccount(customerID_text.getText(), name_text.getText(),
				contact_text.getText(), address_text.getText());
		create(account);
	}

	@FXML
	void edit_event(ActionEvent event) {
		System.out.println(customerID_text.getText());
		String editStmt;
		int rowsAffected;
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement();) {
			editStmt = "UPDATE Customer SET " + edit_choice.getValue() + " = " + "\'" + edit_text.getText() + "\'"
					+ " WHERE CustomerID == " + edit_id_text.getText() + ";";
			rowsAffected = stmt.executeUpdate(editStmt);
			System.out.println("Rows inserted: " + rowsAffected);
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
		delete(Integer.parseInt(delete_id_text.getText()));
	}

	@Override
	public void create(Object obj) {
		if (obj instanceof CustomerAccount) {
			CustomerAccount account = (CustomerAccount) obj;
			String add_customer = "INSERT INTO Customer (CustomerID, Name, ContactDetails, Address) VALUES ("
					+ account.getId() + "," + "\'" + account.getName() + "\'" + "," + "\'" + account.getEmail() + "\'"
					+ "," + "\'" + account.getAddress() + "\'" + ");";
			System.out.println(add_customer);
			Connection conn = DBUtil.connect();
			try (Statement stmt = conn.createStatement();) {
				int rowsAffected = stmt.executeUpdate(add_customer);
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
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String deleteStmt = "DELETE FROM Customer WHERE CustomerID == " + id + ";";
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement();) {
			int rowsAffected = stmt.executeUpdate(deleteStmt);
			System.out.println("Rows inserted: " + rowsAffected);
			initialize();
		} catch (SQLException e) {
			System.out.println("Database error in executeAdd.");
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

	@Override
	public void backToLogin() {
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
		loginStage.close();
		try {
			Login app = new Login();
			Stage mainStage = new Stage();
			app.start(mainStage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openCargo() {
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
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
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
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
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
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
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
		// loginStage.close();
		try {
			AdminAccessCheck app = new AdminAccessCheck();
			Stage mainStage = new Stage();
			app.start(mainStage);
			AdminAccessCheck.previousStage = loginStage;
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openInvoice() {
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
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
		Stage loginStage = (Stage) customerID_text.getScene().getWindow();
		loginStage.close();
		try {
			if (UserPrivilege.accessCustomer(CurrentUser.getUser().getPrivilege())) {
				Customer app = new Customer();
				Stage mainStage = new Stage();
				app.start(mainStage);
				return;
			} else {
				UserPrivilege.deny();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
