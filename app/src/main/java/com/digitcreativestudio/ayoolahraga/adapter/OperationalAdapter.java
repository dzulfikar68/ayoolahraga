package com.digitcreativestudio.ayoolahraga.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Operational;

import java.util.ArrayList;

public class OperationalAdapter extends RecyclerView.Adapter<OperationalAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Operational> list;

    public OperationalAdapter(Context context) {
        this.context = context;
    }

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
