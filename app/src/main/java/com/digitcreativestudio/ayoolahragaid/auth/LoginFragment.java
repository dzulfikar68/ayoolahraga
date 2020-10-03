package com.digitcreativestudio.ayoolahragaid.auth;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.main.setting.TutorialActivity;
import com.digitcreativestudio.ayoolahragaid.network.ClientServices;
import com.digitcreativestudio.ayoolahragaid.network.LoginResponse;
import com.digitcreativestudio.ayoolahragaid.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahragaid.utils.Constant.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private LoginResponse dataLogin = new LoginResponse();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        Button btnLogin = view.findViewById(R.id.btn_login);
        Button btnRegister = view.findViewById(R.id.btn_register);
        Button btnKeTutorial = view.findViewById(R.id.btn_ke_tutorial);

        btnKeTutorial.setOnClickListener(v -> {
            Intent mIntent = new Intent(getActivity(), TutorialActivity.class);
            startActivity(mIntent);
        });

        btnLogin.setOnClickListener(v -> {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                    "Loading. Please wait...", true);

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
                    dataLogin = response.body();
                    SharedPrefManager.getInstance(getActivity())
                            .setLogin(dataLogin.getData());

//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
//                    Objects.requireNonNull(getActivity()).finish();

                    Toast.makeText(getActivity(), dataLogin.getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Objects.requireNonNull(getActivity()).finish();
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        });

        btnRegister.setOnClickListener(v -> {
            RegisterFragment register = new RegisterFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, register, register.getClass().getSimpleName())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
