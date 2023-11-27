package com.example.movieaplication.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Response.PopularMovieResponses;
import com.example.movieaplication.Response.SearchMovieResponses;
import com.example.movieaplication.Utils.AppExecutor;
import com.example.movieaplication.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class SearchMovieApiClient {
    private static SearchMovieApiClient instance;
    public static SearchMovieApiClient getInstance(){
        if (instance == null){
            instance = new SearchMovieApiClient();
        }
        return instance;
    }

    //Live Data
    private MutableLiveData<List<MovieModel>> searchMovieLiveData;


    //global variable runnable
    private SearchRunnable searchRunnable;


    private SearchMovieApiClient() {
        searchMovieLiveData = new MutableLiveData<>();
    }


    //kita akan nge parsing dulu dari popular movie
    public LiveData<List<MovieModel>> getSearchMovie(){
        return searchMovieLiveData;
    }

    public void getSearchMovie(String query, int page) {
        if (searchRunnable != null){
            searchRunnable = null;
        }

        searchRunnable = new SearchRunnable(query, page);
        final Future handler = AppExecutor.getInstance().getNetworkID().submit(searchRunnable);

        AppExecutor.getInstance().getNetworkID().schedule(() -> {
            //cancelling retrofit call
            handler.cancel(true);
        },3000, TimeUnit.MILLISECONDS);
    }

    //retriview data form rest API using runnable
    private class SearchRunnable implements Runnable {

        private String query;
        private final int page;
        boolean cancleRequest;

        public SearchRunnable(String query, int page){
            this.query = query;
            this.page = page;
            cancleRequest = false;
        }

        @Override
        public void run(){
            try {
                Response response = getSearchMovie(query,page).execute();

                if (cancleRequest){
                    return;
                }

                if (response.isSuccessful()){
                    //retriview data
                    //jadi ketika response ini sukses akan tampil 200-299 itu sukses
                    if (response.code() == 200){
                        assert  response.body() != null;
                        List<MovieModel> searchMovieList = new ArrayList<>(((SearchMovieResponses)response.body()).getMovies());

                        //kita cek jika page sama dengan 1 maka dia akan menampikan popular movie live data sedangkan
                        //jika tidak maka dia akan menampilkan list movie model

                        if (page == 1) {
                            //send data to live data
                            //post value -> using background thread
                            //set value
                            searchMovieLiveData.postValue(searchMovieList);
                        } else {
                            List<MovieModel> currentMovie = searchMovieLiveData.getValue();
                            assert currentMovie != null;
                            //disini kita add semua yang ada di List<MovieModel> di atas
                            currentMovie.addAll(searchMovieList);
                            searchMovieLiveData.postValue(currentMovie);

                        }
                    } else {
                        assert response.errorBody() != null;
                        System.out.println(response.errorBody().string());
                        searchMovieLiveData.postValue(null);
                    }
                } else {
                    System.out.println("Request isnt successful");
                    //response.message();
                }

            } catch (IOException e) {
                System.out.println(e);
                searchMovieLiveData.postValue(null);
            }
        }


        //method getPopularity
        private Call<SearchMovieResponses> getSearchMovie(String query, int page){
            return ApiService.getMovieApi().getSearchMovie(Credentials.KEY, query, page);
        }
    }

}
