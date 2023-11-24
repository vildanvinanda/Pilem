package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.PopularMovieApiClient;

import java.util.List;

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

    private PopularMovieRepository(){
        popularMovieApiClient = PopularMovieApiClient.getInstance();
    }

    //nanati live data disini dikirim ke
    public LiveData<List<MovieModel>> getPopularMovie(){
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
