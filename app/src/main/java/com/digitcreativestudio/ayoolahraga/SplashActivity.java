package com.digitcreativestudio.ayoolahraga;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.digitcreativestudio.ayoolahraga.auth.AuthActivity;
import com.digitcreativestudio.ayoolahraga.main.MainActivity;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
