package layout;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminAccessCheck extends Application{
	static Stage previousStage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AdminAccessCheck.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("AdminAccessCheck");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

