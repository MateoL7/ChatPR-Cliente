package model;

public class User {

	public String type = "User";
	private String username;
	
	
	
	public User() {}
	
	public User(String username) {
		super();
		this.username = username;
	}



	public String getType() {
		return type;
	}



	public String getUsername() {
		return username;
	}
	
}
