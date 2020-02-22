package com.digitcreativestudio.ayoolahraga.auth;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.main.MainActivity;
import com.digitcreativestudio.ayoolahraga.main.setting.TutorialActivity;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.LoginResponse;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;


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

        btnKeTutorial.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent mIntent = new Intent(getActivity(), TutorialActivity.class);
                                                 startActivity(mIntent);
                                             }
                                         });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        dataLogin = response.body();
                        SharedPrefManager.getInstance(getActivity())
                                .setLogin(dataLogin.getData());

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        Toast.makeText(getActivity(), dataLogin.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment register = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, register, register.getClass().getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
