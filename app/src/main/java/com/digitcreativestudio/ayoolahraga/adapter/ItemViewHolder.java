package com.digitcreativestudio.ayoolahraga.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.digitcreativestudio.ayoolahraga.R;

class ItemViewHolder extends RecyclerView.ViewHolder {

    //        ImageView imageType;
    TextView titleType;

    ItemViewHolder(@NonNull View itemView) {
        super(itemView);
//            imageType = itemView.findViewById(R.id.iv_image_type);
        titleType = itemView.findViewById(R.id.tv_title_type);
    }

}