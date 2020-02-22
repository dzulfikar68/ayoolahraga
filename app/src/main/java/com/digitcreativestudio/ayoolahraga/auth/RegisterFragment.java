package com.digitcreativestudio.ayoolahraga.auth;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.SplashActivity;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    TextView tvBirth;
    ImageButton btnBirth;
    EditText etName,
             etEmail,
             etPassword,
             etPasswordConfirm,
             etPhone,
             etAddress,
             etHoby;
    Calendar myCalendar;

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
        btnBirth = view.findViewById(R.id.btn_birth);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etPasswordConfirm = view.findViewById(R.id.et_password_confirmation);
        etPhone = view.findViewById(R.id.et_phone);
        etAddress = view.findViewById(R.id.et_address);
        etHoby = view.findViewById(R.id.et_hoby);

        Button btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        myCalendar = Calendar.getInstance();
        ImageButton btnBirth = view.findViewById(R.id.btn_birth);
        btnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        tvBirth.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String hoby = etHoby.getText().toString().trim();
                String birth = tvBirth.getText().toString().trim();

                HashMap<String, String> params = new HashMap<>();
                params.put("name_user", name);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirmation", passwordConfirm);
                params.put("phone", phone);
                params.put("hoby", hoby);
                if(!birth.equals("yyyy-MM-dd")) params.put("birth", birth);
                params.put("address_user", address);

                Call<RegisterResponse> request = services.registerPOST(params);
                request.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse data = response.body();

                        if(data != null){
                            if (data.getStatus().equals("true")){
                                Intent intent = new Intent(getActivity(), SplashActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Register Gagal", Toast.LENGTH_LONG).show();
                        Log.e("REGISTER FAILED", t.getMessage());
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}
