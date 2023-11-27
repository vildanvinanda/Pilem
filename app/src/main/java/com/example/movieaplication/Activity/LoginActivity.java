package com.example.movieaplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.movieaplication.Adapter.AdapterImageSlider;
import com.example.movieaplication.Adapter.AdapterTopRatedMovie;
import com.example.movieaplication.Model.ViewModel.PopularMovieListViewModel;
import com.example.movieaplication.Model.ViewModel.TopRatedMovieListViewModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.SessionManager;
import com.example.movieaplication.databinding.ActivityLoginBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sharedPreference;

    private AdapterTopRatedMovie adapterTopRatedMovie;
    private TopRatedMovieListViewModel topRatedMovieListViewModel;

    private Handler sliderHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreference = new SessionManager(this);
        topRatedMovieListViewModel = new ViewModelProvider(this).get(TopRatedMovieListViewModel.class);

        // Observasi perubahan pada daftar film dalam ViewModel
        ObservarsingAnyChangesSliderImageMovie();

        // Meminta daftar film dari ViewModel
        topRatedMovieListViewModel.getTopRatedMovie(1);

        // Setiap 3 detik, otomatis pindah ke item berikutnya
        sliderHandler.postDelayed(sliderRunnable, 3000);

        AllButton();

    }

    private void ObservarsingAnyChangesSliderImageMovie() {
        topRatedMovieListViewModel.getTopRatedMovie().observe(this, sliderItems -> {
            if (sliderItems != null) {
                adapterTopRatedMovie = new AdapterTopRatedMovie(this, sliderItems);
                binding.viewPager.setAdapter(adapterTopRatedMovie);
                adapterTopRatedMovie.notifyDataSetChanged();
            }
        });
    }

    private void AllButton() {
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        LoginActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_email, (RelativeLayout) findViewById(R.id.bottomusername)
                        );
                EditText addusername = (EditText) bottomSheetView.findViewById(com.example.movieaplication.R.id.addusername);
                bottomSheetView.findViewById(R.id.btnclose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addname = addusername.getText().toString().trim();
                        if (TextUtils.isEmpty(addname)) {
                            addusername.requestFocus();
                            addusername.setError("You have not entered a username");
                        } else if (!addname.equals("admin")) {
                            addusername.requestFocus();
                            addusername.setError("Please fill in your username with the word (admin) ");

                        }else {
                            String username = addusername.getText().toString();
                            openModalPassword(username);

                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void openModalPassword(String username) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                LoginActivity.this, R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.modal_password, (RelativeLayout) findViewById(R.id.bottompassword)
                );
        EditText addpassword = (EditText) bottomSheetView.findViewById(com.example.movieaplication.R.id.addpassword);
        bottomSheetView.findViewById(R.id.btnclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetView.findViewById(R.id.btnlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addname = addpassword.getText().toString().trim();
                if (TextUtils.isEmpty(addname)) {
                    addpassword.requestFocus();
                    addpassword.setError("You have not entered a username");
                } else if (addname.equals("admin")) {
                    sharedPreference.createLoginSession(username);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    addpassword.requestFocus();
                    addpassword.setError("Please fill in your username with the word (admin) ");
                }
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hentikan handler saat activity dihancurkan untuk menghindari memory leak
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = binding.viewPager.getCurrentItem();
            int itemCount = adapterTopRatedMovie.getItemCount();

            if (currentItem < itemCount - 1) {
                // Jika bukan elemen terakhir, pindah ke elemen berikutnya
                binding.viewPager.setCurrentItem(currentItem + 1);
            } else {
                // Jika elemen terakhir, kembali ke elemen pertama
                binding.viewPager.setCurrentItem(0);
            }

            sliderHandler.postDelayed(this, 3000);
        }
    };
}