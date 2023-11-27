package com.example.movieaplication.Repositories;

import androidx.lifecycle.LiveData;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Request.SliderImageMovieApiClient;


import java.util.List;

public class SliderImageMovieRepository {
    private static SliderImageMovieRepository instance;
    private SliderImageMovieApiClient sliderImageMovieApiClient;

    private int page;

    public static SliderImageMovieRepository getInstance() {
        if (instance == null) {
            instance = new SliderImageMovieRepository();
        }

        return instance;
    }

    public SliderImageMovieRepository(){
        sliderImageMovieApiClient = SliderImageMovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getSliderImageMovie(){
        //data ini juga ngambil dari API Client
        return sliderImageMovieApiClient.getSliderImageMovie();
    }
    public void getSliderImageMovie (int page) {
        this.page = page;
        sliderImageMovieApiClient.getSliderImageMovie(page);
    }
    //next page
    public void sliderimageMovieNextPage(){
        getSliderImageMovie(page+1);
    }

}
