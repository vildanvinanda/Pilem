package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.RatedMovieApiClient;
import com.example.movieaplication.Request.UpcomingMovieApiClient;

import java.util.List;

public class RatedMovieRepository {
    private static RatedMovieRepository instance;
    private RatedMovieApiClient topratedMovieApiClient;

    private int page;

    public static RatedMovieRepository getInstance() {
        if (instance == null) {
            instance = new RatedMovieRepository();
        }

        return instance;
    }

    public RatedMovieRepository(){
        topratedMovieApiClient = RatedMovieApiClient.getInstance();
    }

    //ini buat dikirim ke VIewModel
    public LiveData<List<MovieModel>> getTopRatedMovie(){
        //data ini juga ngambil dari API Client
        return topratedMovieApiClient.getTopRatedMovie();
    }
    //ini buat dikirim ke VIewModel
    public void getTopRatedMovie (int page) {
        this.page = page;
        topratedMovieApiClient.getTopRatedMovie(page);
    }

    //next page
    public void topratedMovieNextPage(){
        getTopRatedMovie(page+1);
    }

}
