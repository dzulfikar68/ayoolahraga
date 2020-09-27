package com.digitcreativestudio.ayoolahraga.main;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.main.blog.DetailBlogActivity;
import com.digitcreativestudio.ayoolahraga.model.Blog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLanguage = view.findViewById(R.id.btn_language);
        btnLanguage.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });

        Button btnTutorial = view.findViewById(R.id.btn_tutorial);
        btnTutorial.setOnClickListener(v -> {
//            Intent mIntent = new Intent(getActivity(), TutorialActivity.class);
            Blog blog = new Blog();
//            blog.setLink("http://tutorial.ayoolahraga.id/");
            blog.setLink("https://google.com/");
            Intent mIntent = new Intent(getActivity(), DetailBlogActivity.class);
            mIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, blog);
            startActivity(mIntent);
        });

        Button btnAbout = view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(v -> {
//            Intent mIntent = new Intent(getActivity(), AboutActivity.class);
            Blog blog = new Blog();
//            blog.setLink("http://about.ayoolahraga.id/");
            blog.setLink("https://facebook.com/");
            Intent mIntent = new Intent(getActivity(), DetailBlogActivity.class);
            mIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, blog);
            startActivity(mIntent);
        });
    }
}
