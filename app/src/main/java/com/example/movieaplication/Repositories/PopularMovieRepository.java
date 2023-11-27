package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.PopularMovieApiClient;
import com.example.movieaplication.Response.PopularMovieResponses;

import java.util.List;

import retrofit2.Call;

public class PopularMovieRepository {
    private static PopularMovieRepository instance;
    private PopularMovieApiClient popularMovieApiClient;

    private int page;

    public static PopularMovieRepository getInstance() {
        if (instance == null) {
            instance = new PopularMovieRepository();
        }

        return instance;
    }

    public PopularMovieRepository(){
        popularMovieApiClient = PopularMovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getPopularMovie(){
        //data ini juga ngambil dari API Client
        return popularMovieApiClient.getPopularMovie();
    }
    public void getPopularMovie (int page) {
        this.page = page;
        popularMovieApiClient.getPopularMovie(page);
    }
    //next page
    public void popularMovieNextPage(){
        getPopularMovie(page+1);
    }

}
