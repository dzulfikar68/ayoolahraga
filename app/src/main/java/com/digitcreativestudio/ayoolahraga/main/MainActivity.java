package com.digitcreativestudio.ayoolahraga.main;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.auth.AuthActivity;
import com.digitcreativestudio.ayoolahraga.main.venue.SearchVenueActivity;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private SearchView searchView;
    private Boolean isLogin;
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
                if (isLogin == null || !isLogin) {
                    Intent intent = new Intent(this, AuthActivity.class);
                    startActivity(intent);
                    return false;
                }
                setFragment(new AccountFragment());
                return true;
            case R.id.navigation_setting:
                setFragment(new SettingFragment());
                return true;
        }
        return false;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchview, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ComponentName componentName = new ComponentName(getApplicationContext(), MainActivity.class);//getComponentName();
        if (searchManager != null) {
            SearchableInfo info = searchManager.getSearchableInfo(componentName);
            searchView.setSearchableInfo(info);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchVenue(query);
                    return true;
                }
            });

        }

        return true;
    }

    private void searchVenue(String query) {
        Intent moveWithObjectIntent = new Intent(getApplicationContext(), SearchVenueActivity.class);
        moveWithObjectIntent.putExtra(SearchVenueActivity.EXTRA_INTENT, query);
        startActivity(moveWithObjectIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.bg_actionbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setFragment(new HomeFragment());

        navigation = findViewById(R.id.navigation);
        setHomeClicked();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void setHomeClicked() {
        navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_layout);
        if (f instanceof HomeFragment) {
            setHomeClicked();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        }
    }
}
