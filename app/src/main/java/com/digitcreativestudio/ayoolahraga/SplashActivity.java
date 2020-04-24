package com.digitcreativestudio.ayoolahraga;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.digitcreativestudio.ayoolahraga.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Intent intent;
        intent = new Intent(this, MainActivity.class);
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilDone) {
            }

            public void onFinish() {
                startActivity(intent);
                finish();
            }
        }.start();

//        Intent intent;
//        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
//            intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        } else {
//            intent = new Intent(this, AuthActivity.class);
//            startActivity(intent);
//        }
//        finish();
    }
}
