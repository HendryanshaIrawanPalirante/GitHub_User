package com.example.githubuser.Helper;

import android.database.Cursor;

import com.example.githubuser.Db.DatabaseContract;
import com.example.githubuser.Response.Favorite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Favorite> mapCursorToArrayList(Cursor favoriteCursor){

        ArrayList<Favorite> favoriteList = new ArrayList<>();

        while(favoriteCursor.moveToNext()){
            String login = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN));
            String blog = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BLOG));
            String company = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY));
            String avatarUrl =  favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR));
            String name = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME));
            String location = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION));
            favoriteList.add(new Favorite(login, blog, company, avatarUrl, name, location));
        }
        return favoriteList;
    }

}
