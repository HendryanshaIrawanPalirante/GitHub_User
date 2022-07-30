package com.example.githubuser.Db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "user";

    public static final class FavoriteColumns implements BaseColumns{

        public static String LOGIN = "login";
        public static String BLOG = "blog";
        public static String COMPANY = "company";
        public static String AVATAR = "avatar";
        public static String NAME = "name";
        public static String LOCATION = "location";

    }

}
