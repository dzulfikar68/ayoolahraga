package com.digitcreativestudio.ayoolahraga.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitcreativestudio.ayoolahraga.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageType;
    TextView titleType;

    ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageType = itemView.findViewById(R.id.iv_image_type);
        titleType = itemView.findViewById(R.id.tv_title_type);
    }
}
