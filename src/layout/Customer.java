package layout;

import java.io.IOException;
import javafx.fxml.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.*;

public class Customer extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Customer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}