package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.RatedMovieRepository;
import com.example.movieaplication.Repositories.UpcomingMovieRepository;

import java.util.List;

public class TopRatedMovieListViewModel extends ViewModel {

    private RatedMovieRepository ratedMovieRepository;

    public TopRatedMovieListViewModel(){
        ratedMovieRepository = RatedMovieRepository.getInstance();

    }

    //ngabil dari sini
    public LiveData<List<MovieModel>> getTopRatedMovie(){
        //data ini juga diambil dari repository
        return ratedMovieRepository.getTopRatedMovie();
    }

    public void getTopRatedMovie(int page){
        ratedMovieRepository.getTopRatedMovie(page);
    }

    //kita sebelumnya sudah membuat next page ini di repository nah kita panggil disini
    //next page
    public void topratedMovieNextPage() {
        ratedMovieRepository.topratedMovieNextPage();
    }
}
