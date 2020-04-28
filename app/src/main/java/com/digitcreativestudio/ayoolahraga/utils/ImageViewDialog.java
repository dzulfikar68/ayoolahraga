package com.digitcreativestudio.ayoolahraga.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.digitcreativestudio.ayoolahraga.R;

import java.util.Objects;

public class ImageViewDialog {

    //    public final static String KEY = "link_image";
//    public final static String KEY_LIST = "list_image";
    private Context context;
    private String url;
//    private ArrayList<String> list_image;
//    private int position;

    public ImageViewDialog(Context context, String url) {
        this.context = context;
        this.url = url;
    }

//    public ImageViewDialog(Context context, ArrayList<String> list_image, int position) {
//        this.context = context;
//        this.list_image = list_image;
//        this.position = position;
//    }
//
//    public void list_load(){
//        intentSlider();
//    }
//
//    public void load(){
////        dialog();
//        intent();
//    }
//
//    private void intentSlider(){
//        Intent intent_slider = new Intent(context, ImageListActivity.class);
//        intent_slider.putExtra(ImageViewDialog.KEY_LIST, list_image);
//        intent_slider.putExtra(ImageViewDialog.KEY, position);
//        context.startActivity(intent_slider);
//    }
//
//    private void intent(){
//        Intent intent_image = new Intent(context, ImageViewActivity.class);
//        intent_image.putExtra(ImageViewDialog.KEY, url);
//        context.startActivity(intent_image);
//    }

    @SuppressLint("ClickableViewAccessibility")
    public void dialog() {
        Dialog settingsDialog = new Dialog(context);
        Objects.requireNonNull(settingsDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View image_view = inflater.inflate(R.layout.image_view_layout, null);
        ImageView zoomImage = image_view.findViewById(R.id.image_view);
        zoomImage.setOnTouchListener(new ImageMatrixTouchHandler(context));
        Glide.with(context).load(url).into(zoomImage);
        settingsDialog.setContentView(image_view);
        settingsDialog.setCancelable(true);
        settingsDialog.show();
    }
}
