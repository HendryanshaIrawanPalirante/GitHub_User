package com.example.githubuser.Response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchUser{

	@SerializedName("items")
	private List<ItemsItem> items;

	public List<ItemsItem> getItems(){
		return items;
	}
}