package com.digitcreativestudio.ayoolahragaid.main.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.network.ClientServices;
import com.digitcreativestudio.ayoolahragaid.network.EditAccountResponse;
import com.digitcreativestudio.ayoolahragaid.network.ShowAccountResponse;
import com.digitcreativestudio.ayoolahragaid.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahragaid.utils.Constant.BASE_URL;

public class EditAccountActivity extends AppCompatActivity {

    EditText etName, etAddress, etPhone, etHobby;
    TextView tvImage;
    Button btnEditProfile;
    ClientServices services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etHobby = findViewById(R.id.et_hoby);
        tvImage = findViewById(R.id.tv_image);
        btnEditProfile = findViewById(R.id.btn_edit_profile);

        loadData();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(ClientServices.class);

        btnEditProfile.setOnClickListener(v -> updateData());
    }

    private void loadData(){
        SharedPrefManager preference = SharedPrefManager.getInstance(getApplicationContext());
        etName.setText(preference.getStringPref(SharedPrefManager.KEY_NAME));
        etAddress.setText(preference.getStringPref(SharedPrefManager.KEY_ADDRESS));
        etPhone.setText(preference.getStringPref(SharedPrefManager.KEY_PHONE));
        etHobby.setText(preference.getStringPref(SharedPrefManager.KEY_HOBY));

        String text = preference.getStringPref(SharedPrefManager.KEY_NAME).toUpperCase();
        String finaly = text.charAt(0) + String.valueOf(text.charAt(1));
        tvImage.setText(finaly);
    }


    private void updateData(){
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String hoby = etHobby.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("name_user", name);
        params.put("address_user", address);
        params.put("phone", phone);
        params.put("hoby", hoby);

        final String idUser = SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_ID);
        Call<EditAccountResponse> request = services.editAccountPOST(idUser, params);
        request.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(@NotNull Call<EditAccountResponse> call, @NotNull Response<EditAccountResponse> response) {
                EditAccountResponse dataResponse = response.body();
                if(dataResponse != null){
                    if(dataResponse.getStatus().equals("true")){
                        retrieveData();
                    }
                    Toast.makeText(getApplicationContext(), dataResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Edit Profil Gagal", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    private void retrieveData(){
        String idUser = SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_ID);
        Call<ShowAccountResponse> request = services.showAccountGET(idUser);
        request.enqueue(new Callback<ShowAccountResponse>() {
            @Override
            public void onResponse(Call<ShowAccountResponse> call, Response<ShowAccountResponse> response) {
                ShowAccountResponse dataResponse = response.body();

                if (dataResponse.getData() != null){
                    SharedPrefManager.getInstance(getApplicationContext())
                            .setLogin(dataResponse.getData());
                    finish();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ShowAccountResponse> call, @NotNull Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
