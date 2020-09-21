package view;



import control.ChatController;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;


public class ChatWindow{
	
	//UI Elements

	private Scene scene;
	
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
	
}
