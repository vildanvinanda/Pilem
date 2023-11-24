package com.example.movieaplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieaplication.Model.MovieResponse;
import com.example.movieaplication.Utils.MovieRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel {
    private MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
    }

    public LiveData<MovieResponse> getPopularMovies(String apiKey) {
        MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        movieRepository.getPopularMovies(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }
}
