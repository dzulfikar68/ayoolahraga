package com.digitcreativestudio.ayoolahraga.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.digitcreativestudio.ayoolahraga.R;

import java.util.ArrayList;

public class FacilityAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private Context context;
    private ArrayList<String> list;

    public FacilityAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_facility, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder movieViewHolder, int i) {
//        Glide.with(context)
//                .load(getList().get(i).getImage())
//                .apply(new RequestOptions().override(350, 550))
//                .into(movieViewHolder.imageType);

        movieViewHolder.titleType.setText(getList().get(i));
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }
}

