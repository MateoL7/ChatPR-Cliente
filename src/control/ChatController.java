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
						for(int i = 0; i < buttons.size();i++) {
							if(buttons.get(i).isSelected()) {
								DirectMessage dm = new DirectMessage(id, msg, buttons.get(i).getText());
								String sendDM = gson.toJson(dm);
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
			//Poner los mensajes en el Area
			Platform.runLater(

					()->{
						Message msgObj = gson.fromJson(msg, Message.class);
						view.getChatArea().appendText(msgObj.getId() + " : "+msgObj.getBody()+"\n");
					}

					);
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
							ToggleButton tb = new ToggleButton();
							tb.setText(members.get(i).getUsername());
							tb.setId(members.get(i).getUsername()+"Btt");
							view.getParticipantsVBox().getChildren().add(tb);
							tb.setSelected(false);
							buttons.add(tb);
						}

					}

					);
			break;
		case "DirectMessage":

			DirectMessage theMsg = gson.fromJson(msg, DirectMessage.class);
			String from = theMsg.getFromClient();
			String body = theMsg.getBody();

			Platform.runLater(

					()->{
						view.getChatArea().appendText("(Privado) " + from+" : " + body+"\n");
					}

					);
			break;
			//		case "User":
			//
			//			Platform.runLater(
			//
			//					()->{
			//						User u = gson.fromJson(msg, User.class);
			//						ToggleButton tb = new ToggleButton();
			//						tb.setText(u.getUsername());
			//						tb.setId(username+"Btt");
			//						view.getParticipantsVBox().getChildren().add(tb);
			//						buttons.add(tb);
			//
			//					}
			//
			//					);
			//			break;
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
//
//	public void exit() {
//		Gson gson = new Gson();
//		ConnectionPossible cp = new ConnectionPossible(true);
//		String json = gson.toJson(cp);
//		connection.getEmisor().sendMessage(json);
//	}
//	
	public void unselect(String name) {
		for(int i = 0; i< buttons.size();i++) {
			if(!(buttons.get(i).getText().equalsIgnoreCase(name))) {
				buttons.get(i).setSelected(false);
			}
		}
	}
}
