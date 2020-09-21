package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ConnectionWindow;

public class LauncherClient extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConnectionWindow.fxml"));
		Parent root = fxmlLoader.load();
		ConnectionWindow c = fxmlLoader.getController();
		c.setStage(stage);
		
		
		Scene scene = new Scene(root);
		stage.setTitle("PRChat");
		stage.setScene(scene);
		stage.show();
		
		c.setScene(scene);
		
	}

}
