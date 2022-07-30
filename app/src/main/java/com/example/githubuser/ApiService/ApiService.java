package com.example.githubuser.ApiService;

import com.example.githubuser.Response.DetailUser;
import com.example.githubuser.Response.Followers;
import com.example.githubuser.Response.Following;
import com.example.githubuser.Response.SearchUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface  ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    Call<SearchUser> getSearchUser(@Query("q") String username);

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    Call<DetailUser> getUserDetail(@Path("username") String username);

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    Call<List<Followers>> getFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    Call<List<Following>> getFollowing(@Path("username") String username);

}
