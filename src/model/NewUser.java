package model;

public class NewUser {
	public String type = "NewUser";
	private String username;
	boolean connected;
	
	public NewUser() {}
	
	public NewUser(String username, boolean connected) {
		super();
		this.username = username;
		this.connected = connected;
	}

	public String getUsername() {
		return username;
	}
	

	public String getType() {
		return type;
	}

	public boolean isConnected() {
		return connected;
	}
	
}
