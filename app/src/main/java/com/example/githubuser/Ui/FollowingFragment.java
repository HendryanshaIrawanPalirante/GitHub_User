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

import com.example.githubuser.Adapter.FollowingAdapter;
import com.example.githubuser.ApiService.ApiConfig;
import com.example.githubuser.Db.DatabaseContract;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.R;
import com.example.githubuser.Response.Following;
import com.example.githubuser.Response.ItemsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends Fragment {

    private final ArrayList<Following> listF = new ArrayList<>();
    private RecyclerView rvFollowing;
    private ProgressBar progressBar;
    private String username;

    public FollowingFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFollowing = view.findViewById(R.id.list_following);
        progressBar = view.findViewById(R.id.progressBar_Following);


        ItemsItem itemsItem = getActivity().getIntent().getParcelableExtra("data");
        String login = getActivity().getIntent().getStringExtra("dataF");

        if(itemsItem != null){
            String username = itemsItem.getLogin();
            setFowllowing(username);
        }else{
            FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getActivity());
            favoriteHelper.open();
            Cursor dataCursor = favoriteHelper.queryByLogin(login);
            if(dataCursor.moveToFirst()){
                username = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN));
            }
            setFowllowing(username);
        }

    }

    private void setFowllowing(String username){
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Following>> client = ApiConfig.getApiService().getFollowing(username);
        client.enqueue(new Callback<List<Following>>() {
            @Override
            public void onResponse(@NonNull Call<List<Following>> call, @NonNull Response<List<Following>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body() != null){
                        setListData((ArrayList<Following>) response.body());
                    }
                }else{
                    if (response.body() != null) {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Following>> call, @NonNull Throwable t) {
                Log.d("TAG RESULT", "onFailure: " +t.getMessage());
                Toast.makeText(getContext(), "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    private void setListData(java.util.ArrayList<Following> list){
        for(Following listUsers : list){
            Following following = new Following();
            following.setLogin(listUsers.getLogin());
            following.setAvatarUrl(listUsers.getAvatarUrl());
            listF.add(following);
        }
        rvFollowing.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        FollowingAdapter followingAdapter = new FollowingAdapter(listF);
        rvFollowing.setAdapter(followingAdapter);
    }

}