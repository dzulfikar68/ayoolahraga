package com.digitcreativestudio.ayoolahragaid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digitcreativestudio.ayoolahragaid.R;

import ss.com.bannerslider.ImageLoadingService;

public class GlideImageLoadingService implements ImageLoadingService {

    private Context context;

    public GlideImageLoadingService(Context context){
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                .into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context)
                .load(resource)
//                .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                .error(errorDrawable)
                .into(imageView);
    }
}
