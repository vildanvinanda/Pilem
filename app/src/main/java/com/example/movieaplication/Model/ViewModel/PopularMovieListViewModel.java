package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.PopularMovieRepository;
import com.example.movieaplication.Utils.MovieApi;

import java.util.List;

public class PopularMovieListViewModel {
    private PopularMovieRepository poppularMovieRepository;

    public PopularMovieListViewModel (){
        poppularMovieRepository = PopularMovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getPopularMovie(){
        return poppularMovieRepository.getPopularMovie();
    }

    public void getPopularMovie(int page){
        poppularMovieRepository.getPopularMovie(page);
    }

    //kita sebelumnya sudah membuat next page ini di repository nah kita panggil disini
    //next page
    public void popularMovieNextPage() {
        poppularMovieRepository.popularMovieNextPage();
    }
}
