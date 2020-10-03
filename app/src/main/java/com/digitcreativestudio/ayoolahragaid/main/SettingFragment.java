package com.digitcreativestudio.ayoolahragaid.main;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.main.blog.DetailBlogActivity;
import com.digitcreativestudio.ayoolahragaid.model.Blog;

import java.util.Objects;


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
            blog.setLink("https://ayoolahraga.id/tutorial-aplikasi/");
//            blog.setLink("https://google.com/");
            Intent mIntent = new Intent(getActivity(), DetailBlogActivity.class);
            mIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, blog);
            startActivity(mIntent);
        });

        Button btnAbout = view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(v -> {
//            Intent mIntent = new Intent(getActivity(), AboutActivity.class);
            Blog blog = new Blog();
            blog.setLink("https://about.ayoolahraga.id/");
//            blog.setLink("https://facebook.com/");
            Intent mIntent = new Intent(getActivity(), DetailBlogActivity.class);
            mIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, blog);
            startActivity(mIntent);
        });

        Button btnSaran = view.findViewById(R.id.btn_saran);
        btnSaran.setOnClickListener(v -> {
            Blog blog = new Blog();
            blog.setLink("https://ayoolahraga.id/feedback-aplikasi/");
//            blog.setLink("https://microsoft.com/");
            Intent mIntent = new Intent(getActivity(), DetailBlogActivity.class);
            mIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, blog);
            startActivity(mIntent);
        });

        Button btnEmail = view.findViewById(R.id.btn_email);
        btnEmail.setOnClickListener(v -> {
            String[] TO = {"gladysukmaperdana@email.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pertanyaan");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "halo Ayoolahraga, ");

            try {
                startActivity(Intent.createChooser(emailIntent, "Kirim Email lewat Ayoolahraga"));
//                getActivity().finish();
                Log.e("Finished sending", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "Aplikasi email tidak terinstall.", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnWa = view.findViewById(R.id.btn_wa);
        btnWa.setOnClickListener(v -> {
            PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
            try {
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=+62" +
                        "085786018422" +
                        "&text=" +
                        "halo Ayoolahraga, ";
                sendIntent.setData(Uri.parse(url));

                startActivity(sendIntent);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(getActivity(), "Aplikasi whatsapp tidak ditemukan.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
