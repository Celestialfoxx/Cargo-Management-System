package layout;

import java.io.IOException;
import javafx.fxml.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.*;

public class Login extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
