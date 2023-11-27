package com.example.movieaplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.movieaplication.Adapter.AdapterPopularMovie;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Model.ViewModel.PopularMovieListViewModel;
import com.example.movieaplication.Model.ViewModel.SearchMovieListViewModel;
import com.example.movieaplication.R;
import com.example.movieaplication.databinding.ActivityPopularMovieBinding;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopularMovie extends AppCompatActivity {

    private PopularMovieListViewModel popularMovieListViewModel;
    private SearchMovieListViewModel searchMovieListViewModel;
    private AdapterPopularMovie adapterPopularMovie;

    private ActivityPopularMovieBinding binding;

    private boolean isPopular = true;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopularMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //panggil objek
        popularMovieListViewModel = new ViewModelProvider(this).get(PopularMovieListViewModel.class);
        searchMovieListViewModel = new ViewModelProvider(this).get(SearchMovieListViewModel.class);

        searchSetup();

        //show popular moviews
        popularMovieListViewModel.getPopularMovie(1);

        //sebelumnya kita harus membuat observasi
        //data observer
        ObservarsingAnyChangesPopularMovie();
        ObservarsingAnyChangesSearchMovie();

        binding.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    // Tampilkan hasil selanjutnya di sini
                    if (isPopular) {
                        popularMovieListViewModel.popularMovieNextPage();
                    } else {
                        searchMovieListViewModel.searchMovieNextPage();
                    }
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void searchSetup() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovieListViewModel.getSearchMovie(query,1);
                isPopular = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Jika query pencarian kosong, tampilkan semua film
                    popularMovieListViewModel.getPopularMovie(1);
                    isPopular = true; // Set ke true karena Anda menampilkan semua film (film populer)
                }
                return false;
            }
        });

    }


    private void ObservarsingAnyChangesPopularMovie() {
        //data ini ngambil dari ViewModel
        popularMovieListViewModel.getPopularMovie().observe(this, movieModels -> {
            if (movieModels != null){
                // Set up RecyclerView jika belum diatur
                if (adapterPopularMovie == null) {
                    setupRecyclerView(movieModels);
                } else {
                    // Update data jika RecyclerView sudah diatur
                    adapterPopularMovie.updateData(movieModels);
                }
            }
        });
    }

    private void ObservarsingAnyChangesSearchMovie() {
        //data ini ngambil dari ViewModel
        searchMovieListViewModel.getSearchMovie().observe(this, movieModels -> {
            if (movieModels != null) {
                // Set up RecyclerView jika belum diatur
                if (adapterPopularMovie == null) {
                    setupRecyclerView(movieModels);
                } else {
                    // Update data jika RecyclerView sudah diatur
                    adapterPopularMovie.updateData(movieModels);
                }
            }
        });
    }

    private void setupRecyclerView(List<MovieModel> movieModels) {
        // Setup RecyclerView sekali saja di sini
        adapterPopularMovie = new AdapterPopularMovie(getApplicationContext(), movieModels);
        binding.recycleView.setAdapter(adapterPopularMovie);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    // tampilkan hasil selanjutnya di sini
                    if (isPopular) {
                        popularMovieListViewModel.popularMovieNextPage();
                    } else {
                        searchMovieListViewModel.searchMovieNextPage();
                    }
                }
            }
        });
    }


}