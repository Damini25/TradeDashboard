package com.trdsimul.gamemgmt.dto;

public class UserCredDTO {

	private String username;
	private String password;

	public UserCredDTO() {
		super();
	}

	public UserCredDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
