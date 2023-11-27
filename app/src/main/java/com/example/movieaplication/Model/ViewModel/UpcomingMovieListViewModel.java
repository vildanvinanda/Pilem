package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.PopularMovieRepository;
import com.example.movieaplication.Repositories.UpcomingMovieRepository;

import java.util.List;

public class UpcomingMovieListViewModel extends ViewModel {

    private UpcomingMovieRepository upcomingMovieRepository;


    public UpcomingMovieListViewModel(){
        upcomingMovieRepository = UpcomingMovieRepository.getInstance();

    }

    //ngabil dari sini
    public LiveData<List<MovieModel>> getUpcomingMovie(){
        //data ini juga diambil dari repository
        return upcomingMovieRepository.getUpcomingMovie();
    }

    public void getUpcomingMovie(int page){
        upcomingMovieRepository.getUpcomingMovie(page);
    }

    //kita sebelumnya sudah membuat next page ini di repository nah kita panggil disini
    //next page
    public void popularMovieNextPage() {
        upcomingMovieRepository.upcomingMovieNextPage();
    }
}
