package com.example.movieaplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //ini data yang disimpan di session
    public static final String IS_LOGIN = "isLogin";
    public static final String USERNAME = "username";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(IS_LOGIN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //disini kenapa login data ? karena data yang akan di simpan ada di  LoginData
    public void createLoginSession(String  username) {
        //pas kita klik login kita akan membuat login session
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    //buat sesi  loggout
    public void logoutSession(){
        editor.putBoolean(IS_LOGIN, false);
        editor.clear();
        editor.commit();
    }

    //fungsi yang berguna untuk membuat user tidak masuk lagi ke dalam login activity seblum di logout
    public boolean isLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
    public void updateUserData(String s, String username) {
        // Simpan data pengguna yang diperbarui ke dalam sesi
        editor.putString(USERNAME, username);
        editor.apply();
    }
}
