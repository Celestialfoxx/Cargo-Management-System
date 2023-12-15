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
import cargoJavaBean.Tracking;
import database.DBUtil;
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

public class TrackingController implements Initializable{
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	private String[] queryChoice = { "trackingID", "cargoID", "date", "location", "statusUpdate"};
	
	@FXML
    private Button btnShowNumber;

    @FXML
    private TableColumn<Tracking, Integer> trackingID;

    @FXML
    private ChoiceBox<String> choiceBox1;

    @FXML
    private TableColumn<Tracking, Integer> cargoID;

    @FXML
    private TableColumn<Tracking, Date> date;

    @FXML
    private TableColumn<Tracking, String> location;

    @FXML
    private TableView<Tracking> tableView;

    @FXML
    private TextField textField1;

    @FXML
    private TableColumn<Tracking, String> statusUpdate;
    
    @FXML
	private Label label_top;


    ObservableList<Tracking> trackingList = FXCollections.observableArrayList();
    
    List<ObservableList<Tracking>> list = new ArrayList<ObservableList<Tracking>>();
    
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
			trackingList.clear();
			trackingList = list.remove(list.size()-1);
			tableView.setItems(trackingList);
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
		String query = "select * from Tracking;";
		
	    try (Connection conn = DriverManager.getConnection(URL_OF_DB);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(query)) {
	           while (rs.next()) {
	               // process each row
	               int trackingID = rs.getInt("TrackingID");
	               int cargoID = rs.getInt("CargoID");
	               String location = rs.getString("Location");
	               LocalDate date = LocalDate.parse(rs.getString("Date"));
	               String statusUpdate = rs.getString("StatusUpdate");
	              
	               Tracking t = new Tracking(trackingID, cargoID, date, location, statusUpdate);
	               trackingList.add(t);
	           }
	        
	        trackingID.setCellValueFactory(new PropertyValueFactory<>("trackingID"));
	        cargoID.setCellValueFactory(new PropertyValueFactory<>("cargoID"));
	        date.setCellValueFactory(new PropertyValueFactory<>("date"));
	        location.setCellValueFactory(new PropertyValueFactory<>("location"));
	        statusUpdate.setCellValueFactory(new PropertyValueFactory<>("statusUpdate"));
	        label_str.add("Query");
	        tableView.setItems(trackingList);
			
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
	
	public Tracking fetchQuery() {
		String choice = choiceBox1.getValue();
		String txt = getText();
		System.out.println(choice);
		Tracking s = new Tracking();
		Connection conn = DBUtil.connect();
		StringBuilder query = new StringBuilder();
		query.append("select * from Tracking where ");
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
			ObservableList<Tracking> trackingListCopy = FXCollections.observableArrayList(trackingList);
			list.add(trackingListCopy);
			trackingList.clear();
			while (rs.next()) {
				// process each row
				 int trackingID = rs.getInt("TrackingID");
	               int cargoID = rs.getInt("CargoID");
	               String location = rs.getString("Location");
	                     
	               LocalDate date = LocalDate.parse(rs.getString("Date"));
	               String statusUpdate = rs.getString("StatusUpdate");
	              
	               Tracking t = new Tracking(trackingID, cargoID, date, location, statusUpdate);
	               trackingList.add(t);
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
}
