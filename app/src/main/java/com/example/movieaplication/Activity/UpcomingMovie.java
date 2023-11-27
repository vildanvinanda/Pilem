package com.example.movieaplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.example.movieaplication.Adapter.AdapterUpcomingMovie;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Model.ViewModel.UpcomingMovieListViewModel;
import com.example.movieaplication.databinding.ActivityUpcomingMovieBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class UpcomingMovie extends AppCompatActivity {

    private ActivityUpcomingMovieBinding binding;
    private AdapterUpcomingMovie adapterUpcomingMovie;
    private UpcomingMovieListViewModel upcomingMovieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpcomingMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        upcomingMovieListViewModel = new ViewModelProvider(this).get(UpcomingMovieListViewModel.class);

        ObservarsingAnyChangesUpcomingMovie();

        upcomingMovieListViewModel.getUpcomingMovie(1);
        AllButton();

    }

    private void AllButton() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ObservarsingAnyChangesUpcomingMovie() {
        upcomingMovieListViewModel.getUpcomingMovie().observe(this, movieModels -> {
            if (movieModels != null){
                // Set up RecyclerView jika belum diatur
                if (adapterUpcomingMovie == null) {
                    setupRecyclerView(movieModels);
                } else {
                    // Update data jika RecyclerView sudah diatur
                    adapterUpcomingMovie.updateData(movieModels);
                }
            }
        });
    }


    private void setupRecyclerView(List<MovieModel> movieModels) {
        // Setup RecyclerView sekali saja di sini
        adapterUpcomingMovie = new AdapterUpcomingMovie(getApplicationContext(), movieModels);
        binding.recycleView.setAdapter(adapterUpcomingMovie);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    upcomingMovieListViewModel.popularMovieNextPage();
                }
            }
        });
    }
}