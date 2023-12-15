package layout;

import java.io.IOException;
import javafx.fxml.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.*;

public class Tracking extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Tracking.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Tracking");
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
