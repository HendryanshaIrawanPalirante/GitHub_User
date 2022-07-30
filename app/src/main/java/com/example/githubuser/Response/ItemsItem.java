package com.example.githubuser.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemsItem implements Parcelable {

	@SerializedName("login")
	private String login;

	public ItemsItem() {

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setId(int id) {
		this.id = id;
	}

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("id")
	private int id;

	public ItemsItem(Parcel in) {
		login = in.readString();
		avatarUrl = in.readString();
		id = in.readInt();
	}

	public static final Creator<ItemsItem> CREATOR = new Creator<ItemsItem>() {
		@Override
		public ItemsItem createFromParcel(Parcel in) {
			return new ItemsItem(in);
		}

		@Override
		public ItemsItem[] newArray(int size) {
			return new ItemsItem[size];
		}
	};

	public String getLogin(){
		return login;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getId(){
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(login);
		parcel.writeString(avatarUrl);
		parcel.writeInt(id);
	}
}