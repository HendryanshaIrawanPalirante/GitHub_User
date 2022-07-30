package com.example.githubuser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubuser.R;
import com.example.githubuser.Response.ItemsItem;

import java.util.ArrayList;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {

    private final ArrayList<ItemsItem> listUser;
    private OnItemClickCallBack onItemClickCallBack;
    Context context;

    public ListUserAdapter(Context context, ArrayList<ItemsItem> listUser) {
        this.listUser = listUser;
        this.context = context;
    }

    @NonNull
    @Override
    public ListUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserAdapter.ViewHolder holder, int position) {
        ItemsItem item = listUser.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .circleCrop()
                .into(holder.img);
        holder.tvName.setText(item.getLogin());
        holder.itemView.setOnClickListener(v -> onItemClickCallBack.onItemClicked(listUser.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            img = itemView.findViewById(R.id.img_item_photo);
        }
    }

    public interface OnItemClickCallBack{
        void onItemClicked(ItemsItem itemsItem);
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack;
    }

}
