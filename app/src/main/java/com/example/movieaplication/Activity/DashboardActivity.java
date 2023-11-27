package com.example.movieaplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.movieaplication.Adapter.AdapterImageSlider;
import com.example.movieaplication.Adapter.AdapterPopularMovieDashboard;
import com.example.movieaplication.Adapter.AdapterUpcomingMovie;
import com.example.movieaplication.Model.ViewModel.PopularMovieListViewModel;
import com.example.movieaplication.Model.ViewModel.UpcomingMovieListViewModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.SessionManager;
import com.example.movieaplication.databinding.ActivityDashboardBinding;
import com.example.movieaplication.Model.ViewModel.SliderImageMovieViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    private AdapterImageSlider adapterImageSlider;
    private AdapterPopularMovieDashboard adapterPopularMovie;
    private AdapterUpcomingMovie adapterUpcomingMovie;

    private SliderImageMovieViewModel movieListViewModel;
    private PopularMovieListViewModel popularMovieListViewModel;
    private UpcomingMovieListViewModel upcomingMovieListViewModel;

    private Handler sliderHandler = new Handler(Looper.getMainLooper());

    //Session Login and Save Data In Lokal Storage
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        sessionManager = new SessionManager(DashboardActivity.this);
        if (sessionManager.isLogin() == false){
            moveToLogin();
        }

        popularMovieListViewModel = new ViewModelProvider(this).get(PopularMovieListViewModel.class);
        upcomingMovieListViewModel = new ViewModelProvider(this).get(UpcomingMovieListViewModel.class);
        movieListViewModel = new ViewModelProvider(this).get(SliderImageMovieViewModel.class);

        // Observasi perubahan pada daftar film dalam ViewModel
        ObservarsingAnyChangesSliderImageMovie();
        ObservarsingAnyChangesPopularMovie();
        ObservarsingAnyChangesUpcomingMovie();

        // Meminta daftar film dari ViewModel
        movieListViewModel.getSilderImageMovie(1);
        popularMovieListViewModel.getPopularMovie(1);
        upcomingMovieListViewModel.getUpcomingMovie(1);

        // Setiap 3 detik, otomatis pindah ke item berikutnya
        sliderHandler.postDelayed(sliderRunnable, 3000);

        AllButton();

    }

    private void AllButton() {
        binding.btnViewAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PopularMovie.class);
                startActivity(intent);
            }
        });
        binding.btnViewAllUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, UpcomingMovie.class);
                startActivity(intent);
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLogout();
            }
        });
        binding.btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, sessionManager.getUsername().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void moveToLogout() {
        sessionManager.logoutSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        //menggunakan Flag_Activity_No_History berguna suaya data yang ada di dashboard activity ini gk adak lagi
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void ObservarsingAnyChangesUpcomingMovie() {
        upcomingMovieListViewModel.getUpcomingMovie().observe(this, movieModels -> {
            if (movieModels != null) {
                // kita luping
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.recUpcoming.setLayoutManager(layoutManager);
                adapterUpcomingMovie = new AdapterUpcomingMovie(getApplicationContext(), movieModels);
                binding.recUpcoming.setAdapter(adapterUpcomingMovie);
            }
        });
    }

    private void ObservarsingAnyChangesPopularMovie() {
        popularMovieListViewModel.getPopularMovie().observe(this, movieModels -> {
            if (movieModels != null) {
                // kita luping
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.recPopular.setLayoutManager(layoutManager);
                adapterPopularMovie = new AdapterPopularMovieDashboard(getApplicationContext(), movieModels);
                binding.recPopular.setAdapter(adapterPopularMovie);
            }
        });
    }

    private void ObservarsingAnyChangesSliderImageMovie() {
        movieListViewModel.getSliderImageMovie().observe(this, sliderItems -> {
            if (sliderItems != null) {
                adapterImageSlider = new AdapterImageSlider(this, sliderItems);
                binding.viewPager.setAdapter(adapterImageSlider);
                adapterImageSlider.notifyDataSetChanged();

                // Setup dots indicator
                setupDotsIndicator();
            }
        });
    }

    private void setupDotsIndicator() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabIndicator, binding.viewPager, (tab, position) -> {
            // Optional: Customize your tab here
        });

        // ...

        // Set ukuran dot atau indikator
        binding.tabIndicator.setTabIndicatorFullWidth(false); // Sesuaikan dengan preferensi Anda
        tabLayoutMediator.attach();
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = binding.viewPager.getCurrentItem();
            int itemCount = adapterImageSlider.getItemCount();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hentikan handler saat activity dihancurkan untuk menghindari memory leak
        sliderHandler.removeCallbacks(sliderRunnable);
    }


}