package com.digitcreativestudio.ayoolahraga.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.digitcreativestudio.ayoolahraga.model.User;

public class SharedPrefManager {

    private static final String SHARE_PREF_NAME = "user";
    public static final String KEY_ID = "id_user";
    public static final String KEY_NAME = "name_user";
    public static final String KEY_EMAIL = "email_user";
    public static final String KEY_ADDRESS = "address_user";
    public static final String KEY_PHONE = "phone_user";
    public static final String KEY_HOBY = "hoby_user";
    private static final String KEY_BIRTH = "birth_user";
    private static SharedPrefManager mInstance;
    private static Context mContext;

    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setLogin(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ID, String.valueOf(user.getId()));
        editor.putString(KEY_NAME, user.getName_user());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ADDRESS, user.getAddress_user());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_BIRTH, user.getBirth());
        editor.putString(KEY_HOBY, user.getHoby());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getStringPref(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}
