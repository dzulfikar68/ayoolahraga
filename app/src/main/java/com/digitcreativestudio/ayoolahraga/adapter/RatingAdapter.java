package com.digitcreativestudio.ayoolahraga.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Rating;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ItemViewHolder> {
    private ArrayList<Rating> list;

    public ArrayList<Rating> getList() {
        return list;
    }

    public void setList(ArrayList<Rating> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rating, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder movieViewHolder, int i) {
        movieViewHolder.name.setText(getList().get(i).getName_user());
        movieViewHolder.message.setText(getList().get(i).getMessage());
        movieViewHolder.rating.setRating(Float.valueOf(getList().get(i).getScore()));
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name, message;
        RatingBar rating;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_rating);
            message = itemView.findViewById(R.id.tv_message_rating);
            rating = itemView.findViewById(R.id.rb_rate_rating);
        }
    }
}
