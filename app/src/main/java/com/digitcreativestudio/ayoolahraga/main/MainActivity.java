package com.digitcreativestudio.ayoolahraga.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahraga.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                setFragment(new HomeFragment());
                return true;
            case R.id.navigation_favorite:
                setFragment(new FavoriteFragment());
                return true;
            case R.id.navigation_account:
                setFragment(new AccountFragment());
                return true;
            case R.id.navigation_setting:
                setFragment(new SettingFragment());
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.bg_actionbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setFragment(new HomeFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
    }
}
