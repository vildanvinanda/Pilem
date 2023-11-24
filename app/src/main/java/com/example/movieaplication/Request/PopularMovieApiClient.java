package com.example.movieaplication.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Response.PopularMovieResponses;
import com.example.movieaplication.Utils.AppExecutor;
import com.example.movieaplication.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PopularMovieApiClient {
    private static PopularMovieApiClient instance;
    public static PopularMovieApiClient getInstance(){
        if (instance == null){
            instance = new PopularMovieApiClient();
        }
        return instance;
    }

    //Live Data
    private MutableLiveData<List<MovieModel>> popularMovieLiveData;


    //global variable runnable
    private PoppularRunnable poppularRunnable;


    private PopularMovieApiClient() {
        popularMovieLiveData = new MutableLiveData<>();
    }


    //kita akan nge parsing dulu dari popular movie
    public LiveData<List<MovieModel>> getPopularMovie(){
        return popularMovieLiveData;
    }

    public void getPopularMovie(int page) {
        if (poppularRunnable != null){
            poppularRunnable = null;
        }

        poppularRunnable = new PoppularRunnable(page);
        final Future handler = AppExecutor.getInstance().getNetworkID().submit(poppularRunnable);

        AppExecutor.getInstance().getNetworkID().schedule(() -> {
            //cancelling retrofit call
            handler.cancel(true);
        },3000, TimeUnit.MILLISECONDS);
    }

    //retriview data form rest API using runnable
    private class PoppularRunnable implements Runnable {

        private final int page;
        boolean cancleRequest;

        public PoppularRunnable(int page){
            this.page = page;
            cancleRequest = false;
        }

        @Override
        public void run(){
            try {
                Response response = getPopularMovie(page).execute();

                if (cancleRequest){
                    return;
                }

                if (response.isSuccessful()){
                    //retriview data
                    //jadi ketika response ini sukses akan tampil 200-299 itu sukses
                    if (response.code() == 200){
                        assert  response.body() != null;
                        List<MovieModel> popularMovieList = new ArrayList<>(((PopularMovieResponses)response.body()).getMovies());

                        //kita cek jika page sama dengan 1 maka dia akan menampikan popular movie live data sedangkan
                        //jika tidak maka dia akan menampilkan list movie model

                        if (page == 1) {
                            //send data to live data
                            //post value -> using background thread
                            //set value
                            popularMovieLiveData.postValue(popularMovieList);
                        } else {
                            List<MovieModel> currentMovie = popularMovieLiveData.getValue();
                            assert currentMovie != null;
                            //disini kita add semua yang ada di List<MovieModel> di atas
                            currentMovie.addAll(popularMovieList);
                            popularMovieLiveData.postValue(currentMovie);

                        }
                    } else {
                        assert response.errorBody() != null;
                        System.out.println(response.errorBody().string());
                        popularMovieLiveData.postValue(null);
                    }
                } else {
                    System.out.println("Request isnt successful");
                    //response.message();
                }

            } catch (IOException e) {
                System.out.println(e);
                popularMovieLiveData.postValue(null);
            }
        }


        //method getPopularity
        private Call<PopularMovieResponses> getPopularMovie(int page){
            return ApiService.getMovieApi().getPopularMovie(Credentials.KEY, page);
        }
    }

}
