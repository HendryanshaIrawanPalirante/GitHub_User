package com.example.githubuser.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubuser.Adapter.SectionsPagerAdapter;
import com.example.githubuser.Db.DatabaseContract;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailFavoriteActivity extends AppCompatActivity {

    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail User Favorite");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvUsername = findViewById(R.id.username_favorite);
        TextView tvBlog = findViewById(R.id.blog_favorite);
        TextView tvCompany = findViewById(R.id.company_favorite);
        ImageView img = findViewById(R.id.detail_photo_favorite);
        TextView tvName = findViewById(R.id.name_favorite);
        TextView tvLocation = findViewById(R.id.location_favorite);

        FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        String login = getIntent().getStringExtra("dataF");
        Cursor dataCursor = favoriteHelper.queryByLogin(login);

        if(dataCursor.moveToFirst()){
            String username = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN));
            String blog = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BLOG));
            String company = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY));
            String avatarUrl = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR));
            String name = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME));
            String location = dataCursor.getString(dataCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION));

            tvUsername.setText(username);
            tvBlog.setText(blog);
            tvCompany.setText(company);
            Glide.with(getApplicationContext())
                    .load(avatarUrl)
                    .circleCrop()
                    .into(img);
            tvName.setText(name);
            tvLocation.setText(location);
        }

        setTab();

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

}