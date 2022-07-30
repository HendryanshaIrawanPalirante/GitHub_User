package com.example.githubuser.Response;

import com.google.gson.annotations.SerializedName;

public class DetailUser{

	@SerializedName("login")
	private String login;

	@SerializedName("blog")
	private String blog;

	@SerializedName("company")
	private String company;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private String location;

	public String getLogin(){
		return login;
	}

	public String getBlog(){
		return blog;
	}

	public String getCompany(){
		return company;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getName(){
		return name;
	}

	public String getLocation(){
		return location;
	}

}