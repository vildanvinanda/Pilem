package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.PopularMovieApiClient;
import com.example.movieaplication.Request.UpcomingMovieApiClient;

import java.util.List;

public class UpcomingMovieRepository {
    private static UpcomingMovieRepository instance;
    private UpcomingMovieApiClient upcomingMovieApiClient;

    private int page;

    public static UpcomingMovieRepository getInstance() {
        if (instance == null) {
            instance = new UpcomingMovieRepository();
        }

        return instance;
    }

    public UpcomingMovieRepository(){
        upcomingMovieApiClient = UpcomingMovieApiClient.getInstance();
    }

    //ini buat dikirim ke VIewModel
    public LiveData<List<MovieModel>> getUpcomingMovie(){
        //data ini juga ngambil dari API Client
        return upcomingMovieApiClient.getUpcomingMovie();
    }
    //ini buat dikirim ke VIewModel
    public void getUpcomingMovie (int page) {
        this.page = page;
        upcomingMovieApiClient.getUpcomingMovie(page);
    }

    //next page
    public void upcomingMovieNextPage(){
        getUpcomingMovie(page+1);
    }

}
