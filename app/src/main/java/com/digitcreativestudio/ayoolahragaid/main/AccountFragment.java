package com.digitcreativestudio.ayoolahragaid.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.SplashActivity;
import com.digitcreativestudio.ayoolahragaid.main.account.EditAccountActivity;
import com.digitcreativestudio.ayoolahragaid.utils.SharedPrefManager;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private TextView tvName,
             tvPhone,
             tvEmail,
             tvAddress,
             tvHoby,
             tvImage;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tv_name_user);
        tvEmail = view.findViewById(R.id.tv_email_user);
        tvAddress = view.findViewById(R.id.tv_address_user);
        tvPhone = view.findViewById(R.id.tv_phone_user);
        tvHoby = view.findViewById(R.id.tv_hoby_user);
        tvImage = view.findViewById(R.id.tv_image_user);

        Button btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(v -> {
            Intent moveWithObjectIntent = new Intent(getActivity(), EditAccountActivity.class);
            startActivity(moveWithObjectIntent);
        });

        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("OK", (dialog, id) -> {
                SharedPrefManager.getInstance(getActivity())
                        .logout();

                Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            });
            builder.setNegativeButton("Cancel", (dialog, id) -> {

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        SharedPrefManager preference = SharedPrefManager.getInstance(getActivity());
        tvName.setText(preference.getStringPref(SharedPrefManager.KEY_NAME));
        tvEmail.setText(preference.getStringPref(SharedPrefManager.KEY_EMAIL));
        tvAddress.setText(preference.getStringPref(SharedPrefManager.KEY_ADDRESS));
        tvPhone.setText(preference.getStringPref(SharedPrefManager.KEY_PHONE));
        tvHoby.setText(preference.getStringPref(SharedPrefManager.KEY_HOBY));

        String text = preference.getStringPref(SharedPrefManager.KEY_NAME).toUpperCase();
        String finaly = String.valueOf(text.charAt(0)) + text.charAt(1);
        tvImage.setText(finaly);
    }
}
