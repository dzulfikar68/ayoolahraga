package com.digitcreativestudio.ayoolahraga.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Community;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Community> list;

    public CommunityAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Community> getList() {
        return list;
    }

    public void setList(ArrayList<Community> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder movieViewHolder, int i) {
        String url = getList().get(i).getPhoto().replace("https://", "http://");
        Glide.with(context)
                .load(url)
                .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                .apply(new RequestOptions().override(350, 550))
                .into(movieViewHolder.image);

        movieViewHolder.title.setText(getList().get(i).getName_community());
        movieViewHolder.address.setText(getList().get(i).getDescription_community());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, address;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image_venue);
            title = itemView.findViewById(R.id.tv_title_venue);
            address = itemView.findViewById(R.id.tv_address_venue);
        }
    }
}