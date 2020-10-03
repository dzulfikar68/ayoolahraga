package com.digitcreativestudio.ayoolahragaid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.model.Operational;

import java.util.ArrayList;

public class OperationalAdapter extends RecyclerView.Adapter<OperationalAdapter.ItemViewHolder> {

    private ArrayList<Operational> list;

    public ArrayList<Operational> getList() {
        return list;
    }

    public void setList(ArrayList<Operational> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public OperationalAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_operational, viewGroup, false);
        return new OperationalAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationalAdapter.ItemViewHolder movieViewHolder, int i) {
        movieViewHolder.day.setText(getList().get(i).getDay());
        movieViewHolder.hour.setText(getList().get(i).getHour());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView hour;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.tv_day_operational);
            hour = itemView.findViewById(R.id.tv_hour_operational);
        }
    }
}
