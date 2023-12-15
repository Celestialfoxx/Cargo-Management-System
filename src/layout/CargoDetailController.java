package layout;

import java.net.URL;
import java.lang.reflect.Constructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cargoJavaBean.CargoUtil;
import cargoJavaBean.Item;
import cargoJavaBean.Shipment;
import javaBean.CurrentUser;
import javaBean.UserPrivilege;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CargoDetailController implements Initializable, MenuBarInterface  {
	private String category;
	
	public CargoDetailController() {
		
	}
	public CargoDetailController(String category) {
		this.category = category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	@FXML
	private TableView<Object> tableView;
	
	@FXML
	private TableColumn<Object, Integer> item_id;
	
	@FXML
	private TableColumn<Object, Double> price;
	
	@FXML
	private TableColumn<Object, Boolean> returned;
	
	@FXML
	private TableColumn<Object, Boolean> timeStored;
	
	@FXML
	private TableColumn<Object, Boolean> timeOutStored;
	
	@FXML
	private TableColumn<Object, String> status;
	
	@FXML
	private TextField itid_txt;
	
	@FXML
	private TextField consu_txt;
	
	@FXML
	private CheckBox checkBox;
	
	@FXML
	private Button sell;
	
	@FXML
	private Button inStore;
	
//	@FXML
//	private ChoiceBox<String> choice1;
//	
//	@FXML
//	private ChoiceBox<String> choice2;
	
	
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	private ObservableList<Object> li = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		updateChoiceBox();
	}
	
	
	public void updateTables() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		String query = "select * from "+category+";";
		
	    try (Connection conn = DriverManager.getConnection(URL_OF_DB);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(query)) {
//	    	Class<?> clazz = Class.forName(category);
	    	Class<?> clazz = Class.forName("cargoJavaBean."+category);
	        Constructor<?> constructor = clazz.getConstructor();
	           while (rs.next()) {
	               int id = rs.getInt(category+"_ID");
	               Date date1;
	               Date date2;
	               Timestamp timestamp = rs.getTimestamp("TimeInStorage");
	               if (timestamp!=null) {
	            	   date1 = new java.sql.Date(timestamp.getTime());
	               }
	               else {
	            	   date1 = null;
	               }
	               Timestamp timestamp2 = rs.getTimestamp("TimeOutOfStorage");
	               if (timestamp2!=null) {
	            	   date2 = new java.sql.Date(timestamp2.getTime());
	               }
	               else {
	            	   date2 = null;
	               }
	               Double price = rs.getDouble("Price");
	               Boolean ret = rs.getBoolean("Returned");
	               String status = rs.getString("Status");
	               
	               Item item = new Item(id, price, ret, date1, date2, status);
	               li.add(item);
	           }
	        item_id.setCellValueFactory(new PropertyValueFactory<>("item_id"));
	        timeStored.setCellValueFactory(new PropertyValueFactory<>("time_in_storage"));
	        timeOutStored.setCellValueFactory(new PropertyValueFactory<>("time_out_of_storage"));
	        price.setCellValueFactory(new PropertyValueFactory<>("price"));
	        status.setCellValueFactory(new PropertyValueFactory<>("status"));
	        returned.setCellValueFactory(new PropertyValueFactory<>("returned"));
	        tableView.setItems(li);
	        
			
	    } catch (SQLException e) {
	        System.out.println("Database error in executeQuery.");
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Alert");
	        alert.setHeaderText("Header Text");
	        alert.setContentText("Database error in executeQuery");

	        // Displaying the alert
	        alert.showAndWait();
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	    	System.out.println("clss not found");
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Alert");
	        alert.setHeaderText("Header Text");
	        alert.setContentText("clss not found");

	        // Displaying the alert
	        alert.showAndWait();
	    } catch (NoSuchMethodException e) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Alert");
	        alert.setHeaderText("Header Text");
	        alert.setContentText("clss not found");

	        // Displaying the alert
	        alert.showAndWait();
	    }
	}
	
	
	public void sell() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
	    try {
	        if (itid_txt.getText() != null && !itid_txt.getText().trim().isEmpty() && 
	            consu_txt.getText() != null && !consu_txt.getText().trim().isEmpty()){

	            int itemId = Integer.parseInt(itid_txt.getText().trim());
	            int consumerId = Integer.parseInt(consu_txt.getText().trim());
	            CargoUtil.sell(itemId, category, consumerId);
	            itid_txt.clear();
	            consu_txt.clear();
	            li.clear();
	            updateTables();
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Please enter valid integer values for item ID and consumer ID or shipment details.");
	    }
	}
	
	
	public void addItem() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		boolean isChecked = checkBox.isSelected();
	    CargoUtil.store(category, isChecked);
	    checkBox.setSelected(false);
	    li.clear();
	    updateTables();
	}
//	
//	private String[] queryChoice() {
//	    String query = "select City_Name from City;";
//	    ArrayList<String> cityList = new ArrayList<>();
//
//	    try (Connection conn = DriverManager.getConnection(URL_OF_DB);
//	         Statement stmt = conn.createStatement();
//	         ResultSet rs = stmt.executeQuery(query)) {
//	        
//	        while (rs.next()) {
//	            cityList.add(rs.getString("City_Name"));
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        return new String[0];
//	    }
//	    return cityList.toArray(new String[0]);
//	}
//	
//	private void updateChoiceBox() {
//		choice1.getItems().addAll(queryChoice());
//		choice2.getItems().addAll(queryChoice());
//	    choice1.getSelectionModel().select(0);// TODO Auto-generated method stub
//	    choice2.getSelectionModel().select(0);// TODO Auto-generated method stub
//	    choice1.setOnAction(this::getQueryChoice1);
//	    choice2.setOnAction(this::getQueryChoice2);
//	}
//	
//	public String getQueryChoice1(ActionEvent event) {
//	    String myChoice = choice1.getValue();
//	    System.out.println(myChoice);
//	    return myChoice;
//	}
//	
//	public String getQueryChoice2(ActionEvent event) {
//	    String myChoice = choice2.getValue();
//	    System.out.println(myChoice);
//	    return myChoice;
//	}
//	
	
	@Override
	public void backToLogin() {
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
  	    loginStage.close();
		try {
			if (UserPrivilege.accessCargo(CurrentUser.getUser().getPrivilege())) {
				Cargo app = new Cargo();
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
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) itid_txt.getScene().getWindow();
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
