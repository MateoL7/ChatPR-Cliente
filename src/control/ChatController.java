package control;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.ToggleButton;
import model.ConnectionPossible;
import model.DirectMessage;
import model.Generic;
import model.Members;
import model.Message;
import model.User;
import java.util.ArrayList;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import view.ChatWindow;

public class ChatController implements OnMessageListener{

	private ChatWindow view;
	private TCPConnection connection;

	private String username;
	private ArrayList<ToggleButton> buttons;

	public ChatController(ChatWindow view) {
		buttons = new ArrayList<>();
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);
		Gson gson = new Gson();
		ConnectionPossible cp = new ConnectionPossible(true);
		String confirmation = gson.toJson(cp);
		view.setOnCloseRequest(
				(e)->{
					TCPConnection.getInstance().closeSocket();
				}
				);

		connection.getEmisor().sendMessage(confirmation);

		view.getSendBt().setOnAction(

				(e)->{

					String id = username;
					String msg = view.getMessageTxt().getText();

					if(view.getTodosBtt().isSelected()) {
						unselect(view.getTodosBtt().getText());
						Message msgObj = new Message(id,msg);
						String json = gson.toJson(msgObj);


						connection.getEmisor().sendMessage(json);

						view.getMessageTxt().setText("");
					}else {
						DirectMessage dm = new DirectMessage(id, msg, id);
						String sendDM = gson.toJson(dm);
						connection.getEmisor().sendMessage(sendDM);
						view.getMessageTxt().setText("");
						for(int i = 0; i < buttons.size();i++) {
							if(buttons.get(i).isSelected()) {
								dm = new DirectMessage(id, msg, buttons.get(i).getText());
								sendDM = gson.toJson(dm);
								connection.getEmisor().sendMessage(sendDM);
								view.getMessageTxt().setText("");
							}
						}


					}
				}


				);
	}



	@Override
	public void OnMessage(String msg) {

		Gson gson = new Gson();
		Generic type = gson.fromJson(msg, Generic.class);
		switch (type.getType()) {
		case "Message":
			Message msgObj = gson.fromJson(msg, Message.class);
			String from1 = msgObj.getId();
			if(from1.equalsIgnoreCase(username)) {
				Platform.runLater(

						()->{

							view.getChatArea().appendText("Yo : "+msgObj.getBody()+"\n");
						}

						);
			}else {
				Platform.runLater(

						()->{

							view.getChatArea().appendText(msgObj.getId() + " : "+msgObj.getBody()+"\n");
						}

						);
			}
			break;
		case "Members":
			Members m = gson.fromJson(msg, Members.class);
			ArrayList<User> members = m.getUsers();
			Platform.runLater(

					()->{
						view.getParticipantsVBox().getChildren().clear();
						view.getParticipantsVBox().getChildren().add(view.getTodosBtt());
						view.getTodosBtt().setSelected(true);
						for(int i = 0; i<members.size();i++) {
							String name = members.get(i).getUsername();
							ToggleButton tb = new ToggleButton();
							if(name.equalsIgnoreCase(username)) {
								name = name + " (Yo)";
								tb.setSelected(true);
							}
							tb.setText(name);
							view.getParticipantsVBox().getChildren().add(tb);
							buttons.add(tb);
						}

					}

					);
			break;
		case "DirectMessage":

			DirectMessage theMsg = gson.fromJson(msg, DirectMessage.class);
			String from = theMsg.getFromClient();
			String body = theMsg.getBody();
			String to = theMsg.getToClient();
			if(from.equalsIgnoreCase(to)) {
				Platform.runLater(

						()->{
							view.getChatArea().appendText("(Privado) Yo: " + body+"\n");
						}

						);
			}else {
				Platform.runLater(

						()->{
							view.getChatArea().appendText("(Privado) " + from+" : " + body+"\n");
						}

						);
			}

			break;
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void unselect(String name) {
		for(int i = 0; i< buttons.size();i++) {
			if(!(buttons.get(i).getText().equalsIgnoreCase(name)) && !(buttons.get(i).getText().contains("(Yo)"))) {
				buttons.get(i).setSelected(false);
			}
		}
	}
}
