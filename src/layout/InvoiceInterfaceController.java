package layout;

import java.net.URL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cargoJavaBean.Invoice;
import cargoJavaBean.Shipment;
import database.DBUtil;
import javaBean.CurrentUser;
import javaBean.UserPrivilege;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InvoiceInterfaceController implements Initializable, MenuBarInterface {

	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	private String[] queryChoice = { "invoiceID", "shipmentID", "customerID", "paymentStatus" };
	
    @FXML
    private TableColumn<Invoice, Double> amount;

    @FXML
    private TableColumn<Invoice, Integer> shipmentID;

    @FXML
    private Button btnShowNumber;

    @FXML
    private ChoiceBox<String> choiceBox1;

    @FXML
    private TableColumn<Invoice, Integer> customerID;

    @FXML
    private TableColumn<Invoice, Date> dateIssued;

    @FXML
    private TableColumn<Invoice, Integer> invoiceID;

    @FXML
    private TableColumn<Invoice, String> paymentStatus;
    
    @FXML
	private Label label_top;

    @FXML
    private TableView<Invoice> tableView;

    @FXML
    private TextField textField1;

    ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();
    
    List<ObservableList<Invoice>> list = new ArrayList<ObservableList<Invoice>>();
    
    private List<String> label_str = new ArrayList<String>();
    
    @FXML
	public void ClickMe() {
		System.out.println("I'm clicked!");
		fetchQuery();
	}
    
	public void ClickBack() {
		System.out.println("Back is clicked!");
		popList();
	}
	
	private void popList() {
		if (list.size()>0) {
			invoiceList.clear();
			invoiceList = list.remove(list.size()-1);
			tableView.setItems(invoiceList);
			label_str.remove(label_str.size()-1);
			label_top.setText(label_str.get(label_str.size()-1)); 
		}	
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getSqlLogi();
		updateChoiceBox();
	}
	
	private void getSqlLogi() {
		String query = "select * from Invoice;";
		
	    try (Connection conn = DriverManager.getConnection(URL_OF_DB);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(query)) {
	           while (rs.next()) {
	               // process each row
	               int invoiceID = rs.getInt("InvoiceID");
	               int customerID = rs.getInt("CustomerID");
	               int shipmentID = rs.getInt("ShipmentID");
	               double amount = rs.getDouble("Amount");	       
	               //LocalDate dateIssued = LocalDate.parse(rs.getString("DateIssued"));
	               String paymentStatus = rs.getString("PaymentStatus");
	               LocalDate dateIssued;
	               if(rs.getString("DateIssued")!= null && !rs.getString("DateIssued").isEmpty()) {
	            	   dateIssued = LocalDate.parse(rs.getString("DateIssued"));
	               }else {
	            	   dateIssued = null;
	               }
	               Invoice invoice = new Invoice(invoiceID, customerID, shipmentID, amount, dateIssued, paymentStatus);
	               invoiceList.add(invoice);
	           }
	        
	        invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
	        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
	        shipmentID.setCellValueFactory(new PropertyValueFactory<>("shipmentID"));
	        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        dateIssued.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));
	        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
	        label_str.add("Query");
	        tableView.setItems(invoiceList);
			
	    } catch (SQLException e) {
	        System.out.println("Database error in executeQuery.");
	        e.printStackTrace();
	    }
	}
	
	private void updateChoiceBox() {
		choiceBox1.getItems().addAll(queryChoice);
	    choiceBox1.getSelectionModel().select(0);// TODO Auto-generated method stub
	    choiceBox1.setOnAction(this::getQueryChoice);
	}
	
	public String getQueryChoice(ActionEvent event) {
	    String myChoice = choiceBox1.getValue();
//	    System.out.println(myChoice);
	    return myChoice;
	}
	
	public Invoice fetchQuery() {
		String choice = choiceBox1.getValue();
		String txt = textField1.getText();
		System.out.println(choice);
		Invoice s = new Invoice();
		Connection conn = DBUtil.connect();
		StringBuilder query = new StringBuilder();
		query.append("select * from Invoice where ");
		query.append(choice);
		query.append("=");
		query.append("'");
		query.append(txt);
		query.append("'");
		String ss = choice+"="+txt;
		label_str.add(ss);
		label_top.setText(label_str.get(label_str.size()-1)); 
		try(Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query.toString());){
			ObservableList<Invoice> invoiceListCopy = FXCollections.observableArrayList(invoiceList);
			list.add(invoiceListCopy);
			invoiceList.clear();
			while (rs.next()) {
				// process each row
			   int invoiceID = rs.getInt("InvoiceID");
               int customerID = rs.getInt("CustomerID");
               int shipmentID = rs.getInt("ShipmentID");
               double amount = rs.getDouble("Amount");	       
               
               String paymentStatus = rs.getString("PaymentStatus");
               //LocalDate dateIssued = LocalDate.parse(rs.getString("DateIssued"));
               LocalDate dateIssued;
               if(rs.getString("DateIssued")!= null && !rs.getString("DateIssued").isEmpty()) {
            	   dateIssued = LocalDate.parse(rs.getString("DateIssued"));
               }else {
            	   dateIssued = null;
               }
               Invoice invoice = new Invoice(invoiceID, customerID, shipmentID, amount, dateIssued, paymentStatus);
               invoiceList.add(invoice);
               textField1.setText(null);
			}
		} catch (Exception e) {
			System.out.println("G!");
		}
		DBUtil.closeConnection(conn);
		
		
		
		return s;
	}
	
	private String getText() {
		return textField1.getText();
	}
	
	@Override
	public void backToLogin() {
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
		Stage loginStage = (Stage) textField1.getScene().getWindow();
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
