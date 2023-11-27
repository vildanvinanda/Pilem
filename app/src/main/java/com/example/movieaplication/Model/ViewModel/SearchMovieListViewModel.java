package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.PopularMovieRepository;
import com.example.movieaplication.Repositories.SearchMovieRepository;

import java.util.List;

public class SearchMovieListViewModel extends ViewModel {
    private SearchMovieRepository searchMovieRepository;

    public SearchMovieListViewModel(){
        searchMovieRepository = SearchMovieRepository.getInstance();

    }

    //ngabil dari sini
    public LiveData<List<MovieModel>> getSearchMovie(){
        //data ini juga diambil dari repository
        return searchMovieRepository.getSearchMovie();
    }

    public void getSearchMovie(String query, int page){
        searchMovieRepository.getSearchMovie(query,page);
    }

    //kita sebelumnya sudah membuat next page ini di repository nah kita panggil disini
    //next page
    public void searchMovieNextPage() {
        searchMovieRepository.searchMovieNextPage();
    }
}
