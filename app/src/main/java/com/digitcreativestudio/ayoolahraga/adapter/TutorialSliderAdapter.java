package com.digitcreativestudio.ayoolahraga.adapter;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class TutorialSliderAdapter extends SliderAdapter {

    private int[] imageList;

    public TutorialSliderAdapter(int[] imageList){
        this.imageList = imageList;
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(imageList[position]);
    }
}

