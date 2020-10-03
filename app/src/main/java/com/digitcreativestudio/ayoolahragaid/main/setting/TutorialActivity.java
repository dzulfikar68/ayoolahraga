package com.digitcreativestudio.ayoolahragaid.main.setting;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.adapter.TutorialSliderAdapter;
import com.digitcreativestudio.ayoolahragaid.utils.GlideImageLoadingService;

import ss.com.bannerslider.Slider;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Tutorial");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int[] list = new int[3];
        list[0] = R.drawable.tutorialimg1;
        list[1] = R.drawable.tutorialimg2;
        list[2] = R.drawable.tutorialimg3;

        Slider sdTutorial = findViewById(R.id.sd_tutorial);
        Slider.init(new GlideImageLoadingService(TutorialActivity.this));
        sdTutorial.setAdapter(new TutorialSliderAdapter(list));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
