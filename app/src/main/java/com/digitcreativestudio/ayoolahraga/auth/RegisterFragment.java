package com.digitcreativestudio.ayoolahraga.auth;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.LoginResponse;
import com.digitcreativestudio.ayoolahraga.network.RegisterResponse;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private TextView tvBirth;
    private EditText etName,
             etEmail,
             etPassword,
             etPasswordConfirm,
             etPhone,
             etAddress,
            etHobby;
    private Calendar myCalendar;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBirth = view.findViewById(R.id.tv_birth);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etPasswordConfirm = view.findViewById(R.id.et_password_confirmation);
        etPhone = view.findViewById(R.id.et_phone);
        etAddress = view.findViewById(R.id.et_address);
        etHobby = view.findViewById(R.id.et_hoby);

        Button btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        myCalendar = Calendar.getInstance();
        ImageButton btnBirth = view.findViewById(R.id.btn_birth);
        btnBirth.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(getActivity()), (view1, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String formatDate = "yyyy-MM-dd";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
            tvBirth.setText(sdf.format(myCalendar.getTime()));
        },
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        Button btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                    "Loading. Please wait...", true);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ClientServices services = retrofit.create(ClientServices.class);

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String passwordConfirm = etPasswordConfirm.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String hoby = etHobby.getText().toString().trim();
            String birth = tvBirth.getText().toString().trim();

            HashMap<String, String> params = new HashMap<>();
            params.put("name_user", name);
            params.put("email", email);
            params.put("password", password);
            params.put("password_confirmation", passwordConfirm);
            params.put("phone", phone);
            params.put("hoby", hoby);
            if (!birth.equals("yyyy-MM-dd")) params.put("birth", birth);
            params.put("address_user", address);

            Call<RegisterResponse> request = services.registerPOST(params);
            request.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(@NotNull Call<RegisterResponse> call, @NotNull Response<RegisterResponse> response) {
                    RegisterResponse data = response.body();

                    if (data != null) {
                        if (data.getStatus().equals("true")) {
//                            Intent intent = new Intent(getActivity(), SplashActivity.class);
//                            startActivity(intent);
//                            Objects.requireNonNull(getActivity()).finish();
                            doLogin();
                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RegisterResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getActivity(), "Register Gagal", Toast.LENGTH_LONG).show();
                    Log.e("REGISTER FAILED", Objects.requireNonNull(t.getMessage()));
                    dialog.dismiss();
                }
            });
        });
    }

    private void doLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("password_confirmation", password);

        Call<LoginResponse> request = services.loginPOST(params);
        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.body() != null) {
                    SharedPrefManager.getInstance(getActivity())
                            .setLogin(response.body().getData());
                    Objects.requireNonNull(getActivity()).finish();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                Intent intent = Objects.requireNonNull(getActivity()).getIntent();
                startActivity(intent);
            }
        });
    }
}
