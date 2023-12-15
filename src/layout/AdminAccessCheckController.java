package layout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminAccessCheckController {
    @FXML
    private TextField adminAccount;

    @FXML
    private PasswordField adminKey;

    @FXML
    private Button confirm;
    
    public void clickConfirm() {
    	String account = adminAccount.getText().strip();
    	String key = adminKey.getText().strip();
    	
    	if(isAdmin(account, key)) {
    		Stage loginStage = (Stage) adminAccount.getScene().getWindow();
      	    loginStage.close();
      	    Stage previousStage = AdminAccessCheck.previousStage;
      	    previousStage.close();
      	    
      	    try {
  	          Admin adminApp = new Admin();
  	          Stage mainStage = new Stage();
  	          adminApp.start(mainStage);
  	          return;
      	    } catch (Exception e) {
  	          e.printStackTrace();
      	    }
    	}else {
    		Alert alert = new Alert(AlertType.WARNING);
    	    alert.setTitle("Wrong");
	        alert.setHeaderText("Invalid key");
	        alert.setContentText("The key is not valid");

    	        // 显示警告窗口
	        alert.showAndWait();
    	    
    		adminAccount.clear();
    		adminKey.clear();
    		
    		System.out.println("Invalid key");
    		
    	}
    }
    
    @FXML
    public void keyEvent() {
        confirm.setOnKeyPressed(e -> {
    		switch (e.getCode()) {
    		case ENTER: 
    			clickConfirm();
    			break;
    		default:
    			break;
    		}
        });
    }
    
    boolean isAdmin(String username, String password) {
    	Connection conn = DBUtil.connect();
    	try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from AdminAccount where AdminUsername = '" + username + "' and AdminPassword = '" + password + "'" );){
    		
    		if(rs.next()) {
    			//rs.close();
    			return true;
    		}		
    	}catch (SQLException e) {
			//System.out.println("Database error in executeAdd.");
            e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
    	
    	return false;
    }
}
