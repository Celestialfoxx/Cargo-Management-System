package layout;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cargoJavaBean.Shipment;
import database.DBUtil;
import javaBean.CurrentUser;
import javaBean.UserPrivilege;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LogisticsController implements Initializable, MenuBarInterface  {
	
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	private String[] queryChoice = { "shipmentID", "departureDate", "arrivalDate", "consumerID", "origin", "destination", "transportID" };
	
	@FXML
    private ChoiceBox<String> choiceBox1;
	
	@FXML
	private ImageView back;
	
	@FXML
	private TableView<Shipment> tableView;
	
	@FXML
	private TableColumn<Shipment, Integer> shipmentID;
	
	@FXML
	private TableColumn<Shipment, Integer> consumerID;
	
	@FXML
	private TableColumn<Shipment, Date> departureDate;
	
	@FXML
	private TableColumn<Shipment, Date> arrivalDate;
	
	@FXML
	private TableColumn<Shipment, String> origin;
	
	@FXML
	private TableColumn<Shipment, String> location;
	
	@FXML
	private TableColumn<Shipment, Integer> cargoID;
	
	@FXML
	private TableColumn<Shipment, Integer> itemID;
	

	
	@FXML
	private Label label_top;
	
	@FXML
	private TableColumn<Shipment, String> destination;
	
	@FXML
	private TableColumn<Shipment, Integer> transportID;
	
    @FXML
    private TextField textField1;
    
    ObservableList<Shipment> shipList = FXCollections.observableArrayList();
    
    List<ObservableList<Shipment>> list = new ArrayList<ObservableList<Shipment>>();
    
    private List<String> label_str = new ArrayList<String>();
	
	
	public void ClickMe() {
		System.out.println("I'm clicked!");
		fetchQuery();
	}
	
	public void ShipNow(int shipment_id) {
		String queryCheck = "SELECT DepartureDATE FROM Shipment WHERE ShipmentID = "+"'"+shipment_id+"'";
        String updateUpdate = "UPDATE Shipment SET DepartureDATE = CURRENT_Date WHERE ShipmentID ="+"'"+shipment_id+"'";
        String updateLocation = "UPDATE Shipment SET Location = Origin WHERE ShipmentID ="+"'"+shipment_id+"'";
        Connection conn = DBUtil.connect();
        boolean flag = false;
        try(Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(queryCheck.toString());){
        	if (rs1.next()) {
                String departureDate = rs1.getString("DepartureDATE");
                if (departureDate == null) {
                    // Update departureDATE
                	int rs2 = stmt.executeUpdate(updateUpdate.toString());
                	int rs3 = stmt.executeUpdate(updateLocation.toString());
                	flag = true;
                } else {
                    System.out.println("Departure date is already set.");
                }
            } else {
                System.out.println("Shipment ID not found.");
            }
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DBUtil.closeConnection(conn);
        if (flag) {
        	shipList.clear();
        	getSqlLogi();
        }
	}
	
	public void DeliverNow(int shipment_id) {
		String queryCheck = "SELECT DepartureDate,ArrivalDate FROM Shipment WHERE ShipmentID = "+"'"+shipment_id+"'";
        String updateUpdate = "UPDATE Shipment SET ArrivalDate = CURRENT_Date WHERE ShipmentID ="+"'"+shipment_id+"'";
        String updateLocation = "UPDATE Shipment SET Location = Destination WHERE ShipmentID ="+"'"+shipment_id+"'";
        Connection conn = DBUtil.connect();
        boolean flag = false;
        try(Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(queryCheck.toString());){
        	if (rs1.next()) {
                String departureDate = rs1.getString("DepartureDate");
                String arrivalDate = rs1.getString("ArrivalDate");
                if (departureDate != null && arrivalDate==null) {
                    // Update departureDATE
                	int rs2 = stmt.executeUpdate(updateUpdate.toString());
                	int rs3 = stmt.executeUpdate(updateLocation.toString());
                	flag = true;
                } else if ( arrivalDate!=null) {
                    System.out.println("Arrivalture date is already set.");
                } else {
                	System.out.println("Haven't departure");
                }
            } else {
                System.out.println("Shipment ID not found.");
            }
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DBUtil.closeConnection(conn);
        if (flag) {
        	shipList.clear();
        	getSqlLogi();
        }
	}
	
	
	
	public void ClickBack() {
		System.out.println("Back is clicked!");
		popList();
	}
	
	private void popList() {
		if (list.size()>0) {
			shipList.clear();
			shipList = list.remove(list.size()-1);
			tableView.setItems(shipList);
			label_str.remove(label_str.size()-1);
			label_top.setText(label_str.get(label_str.size()-1)); 
		}	
	}
	

	// Initialize when started
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    getSqlLogi();
	    updateChoiceBox();
	    tableView.setRowFactory(tv -> {
	        TableRow<Shipment> row = new TableRow<>();

	        // 创建一个上下文菜单
	        ContextMenu contextMenu = new ContextMenu();

	        // 创建两个菜单项
	        MenuItem menuItem1 = new MenuItem("ship");
	        MenuItem menuItem2 = new MenuItem("deliver");

	        // 将菜单项添加到上下文菜单中
	        contextMenu.getItems().addAll(menuItem1, menuItem2);

	        // 为每个菜单项设置动作
	        menuItem1.setOnAction(event -> {
	            // 这里编写按钮1的动作
	        	Shipment rowData = row.getItem();
	        	ShipNow(rowData.getShipmentID());
	        	System.out.println("clicked ship");
	        });
	        menuItem2.setOnAction(event -> {
	            // 这里编写按钮2的动作
	        	Shipment rowData = row.getItem();
	        	DeliverNow(rowData.getShipmentID());
	            System.out.println("clicked deliver");
	        });

	        // 设置鼠标事件
	        row.setOnMouseClicked(event -> {
	            if (!row.isEmpty()) {
	                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
	                    Shipment rowData = row.getItem();
	                    System.out.println("double click left" + rowData.getShipmentID());
	                    String ori = rowData.getOrigin();
	                    String pos = rowData.getLocation();
	                    String des = rowData.getDestination();
	                   
	                    openPathWindow(shortestPath.ShortestPathUtil.getPath(ori, des), pos, shortestPath.ShortestPathUtil.getShortestDistance(ori, des));
	                } else if (event.getButton() == MouseButton.SECONDARY) {
	                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
	                }
	            }
	        });

	        return row;
	    });

	}
	
	private void openPathWindow(List<String> cities,String pos,double dis) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Path.fxml"));
            Parent root = loader.load();

            PathController pathController = loader.getController();
            
            pathController.setCities(cities);
            
            pathController.setDis(dis);
            
            pathController.setCurCity(pos);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("The Shortest Distance");
            
            stage.show();

            pathController.updateLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	private void getSqlLogi() {
		String query = "select * from Shipment;";
		
	    try (Connection conn = DriverManager.getConnection(URL_OF_DB);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(query)) {
	           while (rs.next()) {
	               int id = rs.getInt("ShipmentID");
	               LocalDate departureDate;
	               LocalDate arrivalDate;
	               if (rs.getString("departureDate") != null && !rs.getString("departureDate").isEmpty()) {
	            	    departureDate = LocalDate.parse(rs.getString("departureDate"));
	            	} else {
	            	    departureDate = null;
	            	}
	               if (rs.getString("arrivalDate") != null && !rs.getString("arrivalDate").isEmpty()) {
	            	    arrivalDate = LocalDate.parse(rs.getString("arrivalDate"));
	            	} else {
	            	    arrivalDate = null;
	            	}
	               String ori = rs.getString("origin");
	               String des = rs.getString("destination");
	               String loc = rs.getString("location");
	               int car = rs.getShort("cargoID");
	               int trans = rs.getInt("transportID");
	               int itemID = rs.getInt("ItemID");
	               Shipment ship = new Shipment(id, departureDate, arrivalDate, ori, des, car, loc, trans, itemID);
	               shipList.add(ship);
	           }
	        shipmentID.setCellValueFactory(new PropertyValueFactory<>("shipmentID"));
	        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
	        arrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
	        origin.setCellValueFactory(new PropertyValueFactory<>("origin"));
	        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
	        transportID.setCellValueFactory(new PropertyValueFactory<>("transportID"));
	        location.setCellValueFactory(new PropertyValueFactory<>("location"));
	        cargoID.setCellValueFactory(new PropertyValueFactory<>("cargoID"));
	        itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
	        tableView.setItems(shipList);
	        label_str.add("Query");
			
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
	    System.out.println(myChoice);
	    return myChoice;
	}
	
	public Shipment fetchQuery() {
		String choice = choiceBox1.getValue();
		String txt = textField1.getText();
		System.out.println(choice);
		Shipment s = new Shipment();
		Connection conn = DBUtil.connect();
		StringBuilder query = new StringBuilder();
		query.append("select * from Shipment where ");
		query.append(choice);
		query.append("=");
		query.append("'");
		query.append(txt);
		query.append("'");
		String ss = choice+"="+txt;
		label_str.add(ss);
		label_top.setText(label_str.get(label_str.size()-1)); 
		System.out.println(query.toString());
		try(Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query.toString());){
			ObservableList<Shipment> shipListCopy = FXCollections.observableArrayList(shipList);
			list.add(shipListCopy);
			shipList.clear();
			while (rs.next()) {
				int id = rs.getInt("ShipmentID");
				LocalDate departureDate;
	            LocalDate arrivalDate;
                if (rs.getString("departureDate") != null && !rs.getString("departureDate").isEmpty()) {
            	     departureDate = LocalDate.parse(rs.getString("departureDate"));
             	} else {
             	    departureDate = null;
             	}
                if (rs.getString("arrivalDate") != null && !rs.getString("arrivalDate").isEmpty()) {
             	    arrivalDate = LocalDate.parse(rs.getString("arrivalDate"));
             	} else {
             	    arrivalDate = null;
             	}
				String ori = rs.getString("origin");
				String loc = rs.getString("location");
	            int car = rs.getShort("cargoID");
				String des = rs.getString("destination");
				int trans = rs.getInt("transportID");
				int itemID = rs.getInt("ItemID");
				Shipment ship = new Shipment(id, departureDate, arrivalDate, ori, des, car, loc, trans, itemID);
				shipList.add(ship);
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		
		try {
	          AdminAccessCheck app = new AdminAccessCheck();
	          Stage mainStage = new Stage();
	          app.start(mainStage);
	          AdminAccessCheck.previousStage = currStage;
	          return;
	    } catch (Exception e) {
	          e.printStackTrace();
	    }
		
	}
	
	@Override
	public void openInvoice() {
		// TODO Auto-generated method stub
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
		Stage currStage = (Stage) textField1.getScene().getWindow();
  		currStage.close();
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
