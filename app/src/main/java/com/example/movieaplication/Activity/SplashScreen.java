package com.example.movieaplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.SessionManager;
import com.example.movieaplication.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        // Cek apakah pengguna sudah login
        if (!connected(this)) {
            showInternetDialog();
        } else {
            // Cek apakah pengguna sudah login
            if (sessionManager.isLogin()) {
                navigateToDashboard();
            } else {
                navigateToLogin();
            }
        }
    }

    private void navigateToDashboard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private void navigateToLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.modal_lost_connection, findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.btn_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected(SplashScreen.this)) {
                    showInternetDialog();
                } else {
                    if (sessionManager.isLogin()) {
                        navigateToDashboard();
                    } else {
                        navigateToLogin();
                    }
                }
            }
        });
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean connected(SplashScreen splashScreen) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }
}