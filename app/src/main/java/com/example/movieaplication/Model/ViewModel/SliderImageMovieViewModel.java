package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.SliderImageMovieRepository;

import java.util.List;

public class SliderImageMovieViewModel extends ViewModel {
    private SliderImageMovieRepository sliderImageMovieRepository;

    public SliderImageMovieViewModel (){
        sliderImageMovieRepository = SliderImageMovieRepository.getInstance();

    }

    //ngabil dari sini
    public LiveData<List<MovieModel>> getSliderImageMovie(){
        //data ini juga diambil dari repository
        return sliderImageMovieRepository.getSliderImageMovie();
    }

    public void getSilderImageMovie(int page){
        sliderImageMovieRepository.getSliderImageMovie(page);
    }

    //kita sebelumnya sudah membuat next page ini di repository nah kita panggil disini
    //next page
    public void sliderimageMovieNextPage() {
        sliderImageMovieRepository.sliderimageMovieNextPage();
    }
}
