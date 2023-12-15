package layout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.io.FileInputStream;

import cargoJavaBean.Shipment;
import database.DBUtil;
import javaBean.CurrentUser;
import javaBean.UserPrivilege;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CargoController implements MenuBarInterface {

	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";

	@FXML
	private HBox hBox;

	@FXML
	private List<String> list;

	@FXML
	public void initialize() {
		upimgs();
	}

	public void upimgs() {
		hBox.getChildren().clear();
		hBox.setSpacing(50);
		String query = "select distinct Trade_Name from Cargo";
		try (Connection conn = DriverManager.getConnection(URL_OF_DB);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				String category = rs.getString("Trade_Name");
				System.out.println(category);
				Label label = new Label(category);
				label.setAlignment(Pos.CENTER);
				label.setMinWidth(300);
				String imagePath = "src/img/" + category + ".jpg";
				Image image = new Image(new FileInputStream(imagePath));
				ImageView imv = new ImageView(image);
				imv.setFitWidth(300);
				imv.setFitHeight(300);
				imv.setPreserveRatio(true);
				imv.setCursor(Cursor.HAND);
				imv.setOnMouseClicked(event -> {
					try {
						openDetail(category);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				VBox vbox = new VBox(5);
				vbox.getChildren().addAll(label, imv);
				hBox.getChildren().add(vbox);
			}

		} catch (SQLException e) {
			System.out.println("Database error in executeQuery.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Image file not found.");
			e.printStackTrace();
		}

	}

	private void openDetail(String category) throws IOException {
		Stage loginStage = (Stage) hBox.getScene().getWindow();
		loginStage.close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CargoDetail.fxml"));
		Parent root = loader.load();

		CargoDetailController controller = loader.getController();
		controller.setCategory(category);

		Stage newWindow = new Stage();
		newWindow.setTitle(category + " details");
		newWindow.setScene(new Scene(root));
		newWindow.show();
		try {
			controller.updateTables();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void backToLogin() {
		// TODO Auto-generated method stub
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
		
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
		Stage loginStage = (Stage) hBox.getScene().getWindow();
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
