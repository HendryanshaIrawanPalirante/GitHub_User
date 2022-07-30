package com.example.githubuser.Response;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {

    private String login;
    private String blog;
    private String company;
    private String avater_url;
    private String name;
    private String location;


    public Favorite(String login, String blog, String company, String avatarUrl, String name, String location) {
        this.login = login;
        this.blog = blog;
        this.company = company;
        this.avater_url = avatarUrl;
        this.name = name;
        this.location = location;
    }

    public Favorite(Parcel in) {
        login = in.readString();
        blog = in.readString();
        company = in.readString();
        avater_url = in.readString();
        name = in.readString();
        location = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Favorite() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAvater_url() {
        return avater_url;
    }

    public void setAvater_url(String avater_url) {
        this.avater_url = avater_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(blog);
        parcel.writeString(company);
        parcel.writeString(avater_url);
        parcel.writeString(name);
        parcel.writeString(location);
    }
}
