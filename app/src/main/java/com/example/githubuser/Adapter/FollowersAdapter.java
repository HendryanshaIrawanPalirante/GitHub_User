package com.example.githubuser.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubuser.R;
import com.example.githubuser.Response.Followers;

import java.util.ArrayList;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private final ArrayList<Followers> listFollowers;

    public FollowersAdapter(ArrayList<Followers> listFollowers) {
        this.listFollowers = listFollowers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Followers followers = listFollowers.get(position);
        Glide.with(holder.itemView.getContext())
                .load(followers.getAvatarUrl())
                .circleCrop()
                .into(holder.img);
        holder.tvName.setText(followers.getLogin());
    }

    @Override
    public int getItemCount() {
        return listFollowers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.followers_name);
            img = itemView.findViewById(R.id.followers_photo);
        }
    }
}
