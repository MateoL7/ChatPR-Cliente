package view;



import java.io.IOException;

import control.ChatController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;


public class ChatWindow extends Stage{

	//Components
	private TextArea ChatArea;
	private TextField MessageTxt;
	private Button SendBt;
	private VBox ParticipantsVBox;
	private ToggleButton todosBtt;

	//Controller
	private ChatController control;

	//Constructor
	public ChatWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
			Parent root = loader.load();

			ChatArea = (TextArea) loader.getNamespace().get("ChatArea");
			MessageTxt = (TextField) loader.getNamespace().get("MessageTxt");
			SendBt = (Button) loader.getNamespace().get("SendBt");
			ParticipantsVBox = (VBox) loader.getNamespace().get("ParticipantsVBox");
			todosBtt = (ToggleButton) loader.getNamespace().get("todosBtt");


			Scene scene = new Scene(root);
			this.setScene(scene);
			
			control = new ChatController(this);

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public ChatController getControl() {
		return control;
	}

	public TextArea getChatArea() {
		return ChatArea;
	}

	public TextField getMessageTxt() {
		return MessageTxt;
	}

	public Button getSendBt() {
		return SendBt;
	}

	public VBox getParticipantsVBox() {
		return ParticipantsVBox;
	}

	public ToggleButton getTodosBtt() {
		return todosBtt;
	}

}
