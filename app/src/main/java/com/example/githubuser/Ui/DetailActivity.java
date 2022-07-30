package com.example.githubuser.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.githubuser.Adapter.SectionsPagerAdapter;
import com.example.githubuser.ApiService.ApiConfig;
import com.example.githubuser.Db.DatabaseContract;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.R;
import com.example.githubuser.Response.DetailUser;
import com.example.githubuser.Response.ItemsItem;
import com.example.githubuser.databinding.ActivityDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };
    private String login, blog, company, avatarUrl, name, location;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        getUser();
        setTab();

    }

    private void getUser(){
        ItemsItem itemsItem = getIntent().getParcelableExtra("data");
        String username = itemsItem.getLogin();
        Call<DetailUser> client = ApiConfig.getApiService().getUserDetail(username);
        client.enqueue(new Callback<DetailUser>() {
            @Override
            public void onResponse(@NonNull Call<DetailUser> call, @NonNull Response<DetailUser> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){

                        login = response.body().getLogin();
                        blog = response.body().getBlog();
                        company = response.body().getCompany();
                        avatarUrl = response.body().getAvatarUrl();
                        name = response.body().getName();
                        location = response.body().getLocation();

                        binding.username.setText(login);
                        Glide.with(DetailActivity.this)
                                .load(avatarUrl)
                                .circleCrop()
                                .into(binding.detailPhoto);
                        binding.name.setText(name);
                        binding.location.setText(location);
                        binding.company.setText(company);
                        binding.blog.setText(blog);

                    }
                }else{
                    if (response.body() != null) {
                        Toast.makeText(DetailActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailUser> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTab(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    private void insertDatabase(){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FavoriteColumns.LOGIN, login);
        values.put(DatabaseContract.FavoriteColumns.BLOG, blog);
        values.put(DatabaseContract.FavoriteColumns.COMPANY, company);
        values.put(DatabaseContract.FavoriteColumns.AVATAR, avatarUrl);
        values.put(DatabaseContract.FavoriteColumns.NAME, name);
        values.put(DatabaseContract.FavoriteColumns.LOCATION, location);
        favoriteHelper.insert(values);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil")
                .setContentText("Data Disimpan")
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_detail_user, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.add_daftar:
                insertDatabase();
                return true;
            case R.id.list_favorite_menu:
                Intent intent = new Intent(DetailActivity.this, FavoriteActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

}