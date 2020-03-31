package com.digitcreativestudio.ayoolahraga.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahraga.R;

class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView titleType;

    ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        titleType = itemView.findViewById(R.id.tv_title_type);
    }

}