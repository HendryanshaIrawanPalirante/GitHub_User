package com.example.githubuser.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuser.Adapter.FavoriteAdapter;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.Helper.MappingHelper;
import com.example.githubuser.R;
import com.example.githubuser.Response.Favorite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter adapter;
    private RecyclerView rvfavorite;
    private ProgressBar progressBar;
    ArrayList<Favorite> listF = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        rvfavorite = findViewById(R.id.rv_favoriteUser);
        progressBar = findViewById(R.id.progressBarFavorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("List Favorite");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        showRecyclerList();
        readData();

    }

    private void readData(){
        ArrayList<Favorite> listFavorite;
        listF.clear();

        FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
        Cursor dataCursor = favoriteHelper.queryAll();
        listFavorite = MappingHelper.mapCursorToArrayList(dataCursor);

        if(listFavorite.size() > 0){
            progressBar.setVisibility(View.GONE);
            adapter.setListFavorite(listFavorite);
            adapter.getListFavorite();
        }else{
            adapter.setListFavorite(new ArrayList<>());
        }

    }

    private void showRecyclerList(){
        rvfavorite.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter(listF, this);
        rvfavorite.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

}