package com.digitcreativestudio.ayoolahraga.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Venue;

import java.util.ArrayList;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Venue> list;

    public VenueAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Venue> getList() {
        return list;
    }

    public void setList(ArrayList<Venue> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VenueAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull VenueAdapter.ItemViewHolder movieViewHolder, int i) {
        if(getList().get(i).getPhoto()!=null){
            String url = getList().get(i).getPhoto().replace("https://", "http://");
            Log.e("URL", url);
            Glide.with(context)
                    .load(url)
                    .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                    .apply(new RequestOptions().override(350, 550))
                    .into(movieViewHolder.image);
        }

        movieViewHolder.title.setText(getList().get(i).getName_venue());
        movieViewHolder.address.setText(getList().get(i).getAddress_venue());
        movieViewHolder.city.setText(getList().get(i).getCity_text());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, address, city;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image_venue);
            title = itemView.findViewById(R.id.tv_title_venue);
            address = itemView.findViewById(R.id.tv_address_venue);
            city = itemView.findViewById(R.id.tv_city_venue);
        }
    }
}
