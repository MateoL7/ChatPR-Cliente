package view;



import java.io.IOException;

import comm.TCPConnection;
import control.ChatController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;


public class ChatWindow{
	
	//UI Elements

	private Scene scene;
	
	private Stage stage;
	
	@FXML
    private TextArea ChatArea;

    @FXML
    private TextField MessageTxt;

    @FXML
    private Button SendBt;

    @FXML
    private VBox ParticipantsVBox;

    @FXML
    private Label PersonToChatLB;
    
    @FXML
    private ToggleButton todosBtt;

    private ChatController control;

    @FXML
	public void initialize(){
    	control = new ChatController(this);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public ChatController getControl() {
		return control;
	}

	public void setControl(ChatController control) {
		this.control = control;
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

	public Label getPersonToChatLB() {
		return PersonToChatLB;
	}

	public void setUsername(String username) {
		control.setUsername(username);
	}

	public ToggleButton getTodosBtt() {
		return todosBtt;
	}
	
	public void loadWindow(String username) {
		try {
			setUsername(username);
			Parent rootContainer = FXMLLoader.load(getClass().getResource("/view/ChatWindow.fxml"));
			Scene s = new Scene(rootContainer);
			stage.setScene(s);
			stage.setTitle("PRChat");
			stage.show();
			stage.setOnCloseRequest(

					(e)->{

						TCPConnection.getInstance().closeSocket();
//						System.out.println("Cierra");
// 						System.exit(0);

					}

					);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
