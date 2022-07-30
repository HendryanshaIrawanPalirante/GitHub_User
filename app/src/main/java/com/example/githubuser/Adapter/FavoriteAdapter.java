package com.example.githubuser.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubuser.Db.FavoriteHelper;
import com.example.githubuser.R;
import com.example.githubuser.Response.Favorite;
import com.example.githubuser.Ui.DetailFavoriteActivity;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final ArrayList<Favorite> listFavorite;
    Context context;
    FavoriteHelper favoriteHelper;

    public FavoriteAdapter(ArrayList<Favorite> listFavorite, Context context) {
        this.listFavorite = listFavorite;
        this.context = context;
    }

    public ArrayList<Favorite> getListFavorite(){
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite){
        if(listFavorite.size() > 0){
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFavorite);
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        favoriteHelper = FavoriteHelper.getInstance(holder.itemView.getContext());
        favoriteHelper.open();

        String login = listFavorite.get(position).getLogin();
        holder.tvName.setText(listFavorite.get(position).getName());
        Glide.with(holder.itemView.getContext())
                .load(listFavorite.get(position).getAvater_url())
                .circleCrop()
                .into(holder.imageView);
        holder.btnImage.setOnClickListener(v -> new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus Data")
                        .setContentText("Apakah kamu yakin?")
                        .setConfirmText("Ya!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                favoriteHelper.deleteByLogin(login);
                                sweetAlertDialog
                                        .setTitleText("Hapus!")
                                        .setContentText("Data Di Hapus")
                                        .setConfirmText("Ya")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                listFavorite.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, listFavorite.size());
                            }
                        })
                .show());
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvName;
        final ImageView imageView;
        final ImageButton btnImage;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name_favorite);
            imageView = itemView.findViewById(R.id.img_item_photo_favorite);
            btnImage = itemView.findViewById(R.id.button_fav);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Intent intent = new Intent(context, DetailFavoriteActivity.class);
                intent.putExtra("dataF", listFavorite.get(position).getLogin());
                context.startActivity(intent);
            }
        }
    }

}
