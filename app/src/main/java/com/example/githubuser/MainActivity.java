package com.example.githubuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.githubuser.Adapter.ListUserAdapter;
import com.example.githubuser.ApiService.ApiConfig;
import com.example.githubuser.Response.ItemsItem;
import com.example.githubuser.Response.SearchUser;
import com.example.githubuser.Setting.MainViewModel;
import com.example.githubuser.Setting.SettingPreferences;
import com.example.githubuser.Setting.ViewModelFactory;
import com.example.githubuser.Ui.DetailActivity;
import com.example.githubuser.Ui.FavoriteActivity;
import com.example.githubuser.Ui.SwitchThemeActivity;
import com.example.githubuser.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final ArrayList<ItemsItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listUser.setHasFixedSize(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Github User");
        }

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(MainViewModel.class);
        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        searchUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    private void searchUser(){
        SearchView searchView = binding.search;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    return true;
                }else{
                    items.clear();
                    getSearchUser(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchUser(newText);
                return true;
            }
        });
    }

    private void getSearchUser(String username){
        showLoading(true);
        Call<SearchUser> client = ApiConfig.getApiService().getSearchUser(username);
        client.enqueue(new Callback<SearchUser>() {
            @Override
            public void onResponse(@NonNull Call<SearchUser> call, @NonNull Response<SearchUser> response) {
                showLoading(false);
                if(response.isSuccessful()){
                    if(response.body() != null){
                        setListData((ArrayList<ItemsItem>) response.body().getItems());
                    }
                }else{
                    if (response.body() != null) {
                        Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchUser> call, @NonNull Throwable t) {
                showLoading(false);
                Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListData(java.util.ArrayList<ItemsItem> list){

        for (ItemsItem user : list) {
            ItemsItem itemsItem = new ItemsItem();
            itemsItem.setLogin(user.getLogin());
            itemsItem.setAvatarUrl(user.getAvatarUrl());
            items.add(itemsItem);
        }
        binding.listUser.setLayoutManager(new LinearLayoutManager(this));
        ListUserAdapter listUserAdapter = new ListUserAdapter(this, items);
        binding.listUser.setAdapter(listUserAdapter);
        listUserAdapter.setOnItemClickCallBack(data -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    private void showLoading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SwitchThemeActivity.class);
                startActivity(intent);
                return true;
            case R.id.fav_list:
                Intent favIntent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(favIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}