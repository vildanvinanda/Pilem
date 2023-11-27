package com.example.movieaplication.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Response.RatedMovieResponses;
import com.example.movieaplication.Response.UpcomingMovieResponses;
import com.example.movieaplication.Utils.AppExecutor;
import com.example.movieaplication.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RatedMovieApiClient {
    private static RatedMovieApiClient instance;
    public static RatedMovieApiClient getInstance(){
        if (instance == null){
            instance = new RatedMovieApiClient();
        }
        return instance;
    }

    //Live Data
    private MutableLiveData<List<MovieModel>> ratedMovieLiveData;


    //global variable runnable
    private TopRatedMovieRunnable topRatedMovieRunnable;


    private RatedMovieApiClient() {
        ratedMovieLiveData = new MutableLiveData<>();
    }


    //kita akan nge parsing dulu dari popular movie
    public LiveData<List<MovieModel>> getTopRatedMovie(){
        return ratedMovieLiveData;
    }

    public void getTopRatedMovie(int page) {
        if (topRatedMovieRunnable != null){
            topRatedMovieRunnable = null;
        }

        topRatedMovieRunnable = new TopRatedMovieRunnable(page);
        final Future handler = AppExecutor.getInstance().getNetworkID().submit(topRatedMovieRunnable);

        AppExecutor.getInstance().getNetworkID().schedule(() -> {
            //cancelling retrofit call
            handler.cancel(true);
        },3000, TimeUnit.MILLISECONDS);
    }

    //retriview data form rest API using runnable
    private class TopRatedMovieRunnable implements Runnable {

        private final int page;
        boolean cancleRequest;

        public TopRatedMovieRunnable(int page){
            this.page = page;
            cancleRequest = false;
        }

        @Override
        public void run(){
            try {
                Response response = getTopRatedMovie(page).execute();

                if (cancleRequest){
                    return;
                }

                if (response.isSuccessful()){
                    //retriview data
                    //jadi ketika response ini sukses akan tampil 200-299 itu sukses
                    if (response.code() == 200){
                        assert  response.body() != null;
                        List<MovieModel> topratedMovieList = new ArrayList<>(((RatedMovieResponses)response.body()).getTopRatedMovies());
                        if (page == 1) {
                            ratedMovieLiveData.postValue(topratedMovieList);
                        } else {
                            List<MovieModel> currentMovie = ratedMovieLiveData.getValue();
                            assert currentMovie != null;
                            //disini kita add semua yang ada di List<MovieModel> di atas
                            currentMovie.addAll(topratedMovieList);
                            ratedMovieLiveData.postValue(currentMovie);

                        }
                    } else {
                        assert response.errorBody() != null;
                        System.out.println(response.errorBody().string());
                        ratedMovieLiveData.postValue(null);
                    }
                } else {
                    System.out.println("Request isnt successful");
                    //response.message();
                }

            } catch (IOException e) {
                System.out.println(e);
                ratedMovieLiveData.postValue(null);
            }
        }


        //method getPopularity
        private Call<RatedMovieResponses> getTopRatedMovie(int page){
            return ApiService.getMovieApi().getTopRatedMovie(Credentials.KEY, page);
        }
    }

}
