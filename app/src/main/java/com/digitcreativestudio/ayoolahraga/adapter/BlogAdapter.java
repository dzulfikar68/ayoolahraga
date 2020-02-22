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
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Blog;

import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Blog> list;

    public BlogAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Blog> getList() {
        return list;
    }

    public void setList(ArrayList<Blog> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_blog, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdapter.ItemViewHolder movieViewHolder, int i) {
        Glide.with(context)
                .load(getList().get(i).getImage())
                .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
//                .apply(new RequestOptions().override(350, 550))
                .into(movieViewHolder.imageType);

        movieViewHolder.titleType.setText(getList().get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageType;
        TextView titleType;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageType = itemView.findViewById(R.id.iv_image_blog);
            titleType = itemView.findViewById(R.id.tv_title_blog);
        }
    }
}
