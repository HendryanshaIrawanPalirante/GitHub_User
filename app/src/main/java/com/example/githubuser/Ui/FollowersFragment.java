package com.example.githubuser.Ui;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.githubuser.Adapter.FollowersAdapter;
import com.example.githubuser.ApiService.ApiConfig;
import com.example.githubuser.Db.DatabaseContract;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.R;
import com.example.githubuser.Response.Favorite;
import com.example.githubuser.Response.Followers;
import com.example.githubuser.Response.ItemsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragment extends Fragment {

    private final ArrayList<Followers> listF =  new ArrayList<>();
    private RecyclerView rvFollowers;
    private ProgressBar progressBar;
    private String username;

    public FollowersFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar_Followers);


        ItemsItem itemsItem = getActivity().getIntent().getParcelableExtra("data");
        String login = getActivity().getIntent().getStringExtra("dataF");

        if(itemsItem != null){
            String username = itemsItem.getLogin();
            setFollowers(username);
        }else{
            FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getActivity());
            favoriteHelper.open();
            Cursor dataCursor = favoriteHelper.queryByLogin(login);
            if(dataCursor.moveToFirst()){
                username = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN));
            }
            setFollowers(username);
        }

        rvFollowers = view.findViewById(R.id.list_followers);

    }

    private void setFollowers(String username){
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Followers>> client = ApiConfig.getApiService().getFollowers(username);
        client.enqueue(new Callback<List<Followers>>() {
            @Override
            public void onResponse(@NonNull Call<List<Followers>> call, @NonNull Response<List<Followers>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body() != null){
                        setListData((ArrayList<Followers>) response.body());
                    }
                }else{
                    if (response.body() != null) {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Followers>> call, @NonNull Throwable t) {
                Log.d("TAG RESULT", "onFailure: " +t.getMessage());
                Toast.makeText(getContext(), "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    private void setListData(java.util.ArrayList<Followers> list){
        for(Followers listUser : list){
            Followers followers = new Followers();
            followers.setLogin(listUser.getLogin());
            followers.setAvatarUrl(listUser.getAvatarUrl());
            listF.add(followers);
        }
        rvFollowers.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        FollowersAdapter followersAdapter = new FollowersAdapter(listF);
        rvFollowers.setAdapter(followersAdapter);
    }

}