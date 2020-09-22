package view;

import java.io.IOException;

import comm.TCPConnection;
import control.ConnectionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConnectionWindow{

	private Stage stage;

	private Scene scene;

	private ChatWindow cw;

	@FXML
	private TextField UsuarioTF;

	@FXML
	private Button IngresarBtt;

	@FXML
	private Label AnswerLb;

	public ConnectionController cc;

	@FXML
	public void initialize(){
		cc = new ConnectionController(this);		
	}

	public TextField getUsuarioTF() {
		return UsuarioTF;
	}

	public Button getIngresarBtt() {
		return IngresarBtt;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Label getAnswerLb() {
		return AnswerLb;
	}

	public void setAnswerLb(Label answerLb) {
		AnswerLb = answerLb;
	}
	
	public void closeWindow() {
		((Stage) scene.getWindow()).close();
	}

	public void changeWindow(String username) {

		try {
			closeWindow();
//			ChatWindow c = new ChatWindow();
//			c.loadWindow(username);
			//Open the chat window
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			cw = new ChatWindow();
			cw = fxmlLoader.getController();
			cw.setScene(scene);
			stage.setTitle("Chat Window");
			stage.setScene(scene);
			stage.show();
			cw.setUsername(username);
			stage.setOnCloseRequest(

					(e)->{

						TCPConnection.getInstance().closeSocket();
//						System.out.println("Cierra");
// 						System.exit(0);

					}

					);
		}
		catch(IOException e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.initStyle(StageStyle.UTILITY);
			alert.setTitle("Information");
			alert.setHeaderText("WARNING!");
			alert.setContentText("Hubo un problema");

			alert.showAndWait();
		}

	}
	
	public void loadWindow(Stage stage) {
		try {
			Parent rootContainer = FXMLLoader.load(getClass().getResource("/view/ConnectionWindow.fxml"));
			scene = new Scene(rootContainer);
			setStage(stage);
			stage.setScene(scene);
			stage.setTitle("PRChat");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
