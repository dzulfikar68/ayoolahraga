package com.digitcreativestudio.ayoolahragaid.adapter;

import com.digitcreativestudio.ayoolahragaid.model.Image;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private ArrayList<Image> imageList;

    public MainSliderAdapter(ArrayList<Image> imageList){
        this.imageList = imageList;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(imageList.get(position).getUrl_image().replace("https://", "http://"));
    }
}
