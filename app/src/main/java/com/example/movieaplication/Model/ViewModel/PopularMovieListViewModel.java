package com.example.movieaplication.Model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Repositories.PopularMovieRepository;
import com.example.movieaplication.Utils.MovieApi;

import java.util.List;

public class PopularMovieListViewModel extends ViewModel {

    private PopularMovieRepository poppularMovieRepository;

    public PopularMovieListViewModel (){
        poppularMovieRepository = PopularMovieRepository.getInstance();

    }

    //ngabil dari sini
    public LiveData<List<MovieModel>> getPopularMovie(){
        //data ini juga diambil dari repository
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
