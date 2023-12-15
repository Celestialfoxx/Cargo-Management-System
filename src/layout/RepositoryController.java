package layout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cargoJavaBean.Cargo;
import database.DBUtil;
import database.DatabaseOperations;
import javaBean.CurrentUser;
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

public class RepositoryController implements DatabaseOperations, MenuBarInterface {

	@FXML
	private Button add_button;

	@FXML
	private TextField cargoID_text;

	@FXML
	private Button delete_button;

	@FXML
	private TextField delete_id_text;

	@FXML
	private TableColumn<Cargo, String> desColumn;

	@FXML
	private TextField des_text;

	@FXML
	private Button edit_button;

	@FXML
	private ChoiceBox<String> edit_choice;

	@FXML
	private TextField edit_id_text;

	@FXML
	private TextField edit_text;

	@FXML
	private TableColumn<Cargo, Integer> idColumn;

	@FXML
	private MenuItem op_delete;

	@FXML
	private MenuItem op_edit;

	@FXML
	private TableColumn<Cargo, Double> priceColumn;

	@FXML
	private TextField price_text;

	@FXML
	private TableColumn<Cargo, String> statusColumn;

	@FXML
	private TextField status_text;

	@FXML
	private TableView<Cargo> table;

	@FXML
	private TableColumn<Cargo, String> tradeNameColumn;

	@FXML
	private TextField tradeName_text;

	@FXML
	private TableColumn<Cargo, String> typeColumn;

	@FXML
	private TextField type_text;

	@FXML
	private TableColumn<Cargo, Double> volumeColumn;

	@FXML
	private TextField volume_text;

	@FXML
	private TableColumn<Cargo, Double> weightColumn;

	@FXML
	private TextField weight_text;

	private String choice[] = { "CargoID", "Description", "Weight", "Volume", "Type", "Status", "Trade_Name",
			"Original_Price" };
	private ObservableList<String> choiceList = FXCollections.observableArrayList(choice);
	private ObservableList<Cargo> cargoList = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		readAll();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("cargoID"));
		desColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
		volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		tradeNameColumn.setCellValueFactory(new PropertyValueFactory<>("tradeName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
		table.setItems(cargoList);
		edit_choice.setItems(choiceList);
	}

	public String readAll() {
		cargoList.clear();
		Connection conn = DBUtil.connect();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Cargo;")) {
			while (rs.next()) {
				int cargoID = rs.getInt("CargoID");
				String description = rs.getString("Description");
				double weight = rs.getDouble("Weight");
				double volume = rs.getDouble("Volume");
				String type = rs.getString("Type");
				String status = rs.getString("Status");
				String tradeName = rs.getString("Trade_Name");
				double price = rs.getDouble("Original_Price");
				Cargo cargo = new Cargo(cargoID, description, weight, volume, type, status, tradeName, price);
				cargoList.add(cargo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}

	@FXML
	void add_event(ActionEvent event) {
		if (UserPrivilege.repositoryChange(CurrentUser.getUser().getPrivilege())) {
			Cargo cargo = new Cargo(Integer.parseInt(cargoID_text.getText()), des_text.getText(),
					Double.parseDouble(weight_text.getText()), Double.parseDouble(volume_text.getText()),
					type_text.getText(), status_text.getText(), tradeName_text.getText(),
					Double.parseDouble(price_text.getText()));
			create(cargo);
		} else {
			UserPrivilege.deny();
		}
	}

	@FXML
	void delete_event(ActionEvent event) {
		if (UserPrivilege.repositoryChange(CurrentUser.getUser().getPrivilege())) {
			delete(Integer.parseInt(delete_id_text.getText()));
		} else {
			UserPrivilege.deny();
		}
	}

	@FXML
	void edit_event(ActionEvent event) {
		if (UserPrivilege.repositoryChange(CurrentUser.getUser().getPrivilege())) {
			String editStmt;
			int rowsAffected;
			Connection conn = DBUtil.connect();
//		"CargoID", "Description", "Weight", "Volume", "Type", "Status", "Trade_Name", "Original_Price"
			try (Statement stmt = conn.createStatement();) {
				switch (edit_choice.getValue()) {
				case "CargoID":
					editStmt = "UPDATE Cargo SET CargoID = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Description":
					editStmt = "UPDATE Cargo SET Description = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Weight":
					editStmt = "UPDATE Cargo SET Weight = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Volume":
					editStmt = "UPDATE Cargo SET Volume = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Type":
					editStmt = "UPDATE Cargo SET Type = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Status":
					editStmt = "UPDATE Cargo SET Status = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Trade_Name":
					editStmt = "UPDATE Cargo SET Trade_Name = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
					rowsAffected = stmt.executeUpdate(editStmt);
					System.out.println("Rows inserted: " + rowsAffected);
					break;
				case "Original_Price":
					editStmt = "UPDATE Cargo SET Original_Price = " + edit_text.getText() + " WHERE CargoID == "
							+ edit_id_text.getText() + ";";
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
		} else {
			UserPrivilege.deny();
		}
	}

	@Override
	public void create(Object obj) {
		if (obj instanceof Cargo) {
			Cargo cargo = (Cargo) obj;
			String add_cargo = "INSERT INTO Cargo (CargoID, Description, Weight, Volume, Type, Status, Trade_Name, Original_Price) VALUES ("
					+ cargo.getCargoID() + "," + cargo.getDescription() + "," + cargo.getWeight() + ","
					+ cargo.getVolume() + "," + cargo.getType() + "," + cargo.getStatus() + "," + cargo.getTradeName()
					+ "," + cargo.getOriginalPrice() + ");";

			Connection conn = DBUtil.connect();
			try (Statement stmt = conn.createStatement();) {
				int rowsAffected = stmt.executeUpdate(add_cargo);
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
	public Object read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		String deleteStmt = "DELETE FROM Cargo WHERE CargoID == " + id + ";";
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
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
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
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		loginStage.close();
		try {
			if (UserPrivilege.accessCargo(CurrentUser.getUser().getPrivilege())) {
				layout.Cargo app = new layout.Cargo();
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

	@Override
	public void openRepository() {
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		loginStage.close();
		try {
			if (UserPrivilege.accessRepository(CurrentUser.getUser().getPrivilege())) {
				Repository app = new Repository();
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

	@Override
	public void openLogistics() {
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		loginStage.close();
		try {
			if (UserPrivilege.accessLogistics(CurrentUser.getUser().getPrivilege())) {
				Logistics app = new Logistics();
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

	@Override
	public void openAdmin() {
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		//loginStage.close();
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
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		loginStage.close();
		try {
			if (UserPrivilege.accessInvoice(CurrentUser.getUser().getPrivilege())) {
				InvoiceInterface app = new InvoiceInterface();
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
	
	@Override
	public void openCustomer() {
		Stage loginStage = (Stage) cargoID_text.getScene().getWindow();
		loginStage.close();
		// TODO Auto-generated method stub
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
