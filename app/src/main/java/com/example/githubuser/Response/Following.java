package com.example.githubuser.Response;

import com.google.gson.annotations.SerializedName;

public class Following{

	@SerializedName("login")
	private String login;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("id")
	private int id;

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin(){
		return login;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getId(){
		return id;
	}

}