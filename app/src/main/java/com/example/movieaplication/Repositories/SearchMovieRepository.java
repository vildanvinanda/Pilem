package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.SearchMovieApiClient;

import java.util.List;

public class SearchMovieRepository {
    private static SearchMovieRepository instance;
    private SearchMovieApiClient searchMovieApiClient;
    private String query;
    private int page;

    public static SearchMovieRepository getInstance() {
        if (instance == null) {
            instance = new SearchMovieRepository();
        }

        return instance;
    }

    public SearchMovieRepository(){
        searchMovieApiClient = SearchMovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getSearchMovie(){
        //data ini juga ngambil dari API Client
        return searchMovieApiClient.getSearchMovie();
    }

    public void getSearchMovie (String query, int page) {
        this.query = query;
        this.page = page;
        searchMovieApiClient.getSearchMovie(query, page);
    }
    //next page
    public void searchMovieNextPage(){
        getSearchMovie(query,page+1);
    }

}
