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
import com.example.githubuser.Response.Following;

import java.util.ArrayList;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private final ArrayList<Following> listFollowing;

    public FollowingAdapter(ArrayList<Following> listFollowing) {
        this.listFollowing = listFollowing;
    }

    @NonNull
    @Override
    public FollowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.ViewHolder holder, int position) {
        Following following = listFollowing.get(position);
        Glide.with(holder.itemView.getContext())
                .load(following.getAvatarUrl())
                .circleCrop()
                .into(holder.img);
        holder.tvName.setText(following.getLogin());
    }

    @Override
    public int getItemCount() {
        return listFollowing.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.following_name);
            img = itemView.findViewById(R.id.following_photo);

        }
    }

}
