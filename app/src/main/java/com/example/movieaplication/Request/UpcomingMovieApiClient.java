package com.example.movieaplication.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movieaplication.Model.MovieModel;
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

public class UpcomingMovieApiClient {
    private static UpcomingMovieApiClient instance;
    public static UpcomingMovieApiClient getInstance(){
        if (instance == null){
            instance = new UpcomingMovieApiClient();
        }
        return instance;
    }

    //Live Data
    private MutableLiveData<List<MovieModel>> upcomingMovieLiveData;


    //global variable runnable
    private UpcomingRunnable upcomingRunnable;


    private UpcomingMovieApiClient() {
        upcomingMovieLiveData = new MutableLiveData<>();
    }


    //kita akan nge parsing dulu dari popular movie
    public LiveData<List<MovieModel>> getUpcomingMovie(){
        return upcomingMovieLiveData;
    }

    public void getUpcomingMovie(int page) {
        if (upcomingRunnable != null){
            upcomingRunnable = null;
        }

        upcomingRunnable = new UpcomingRunnable(page);
        final Future handler = AppExecutor.getInstance().getNetworkID().submit(upcomingRunnable);

        AppExecutor.getInstance().getNetworkID().schedule(() -> {
            //cancelling retrofit call
            handler.cancel(true);
        },3000, TimeUnit.MILLISECONDS);
    }

    //retriview data form rest API using runnable
    private class UpcomingRunnable implements Runnable {

        private final int page;
        boolean cancleRequest;

        public UpcomingRunnable(int page){
            this.page = page;
            cancleRequest = false;
        }

        @Override
        public void run(){
            try {
                Response response = getUpcomingMovie(page).execute();

                if (cancleRequest){
                    return;
                }

                if (response.isSuccessful()){
                    //retriview data
                    //jadi ketika response ini sukses akan tampil 200-299 itu sukses
                    if (response.code() == 200){
                        assert  response.body() != null;
                        List<MovieModel> upcomingMovieList = new ArrayList<>(((UpcomingMovieResponses)response.body()).getUpcomingMovies());
                        //kita cek jika page sama dengan 1 maka dia akan menampikan popular movie live data sedangkan
                        //jika tidak maka dia akan menampilkan list movie model
                        if (page == 1) {
                            upcomingMovieLiveData.postValue(upcomingMovieList);
                        } else {
                            List<MovieModel> currentMovie = upcomingMovieLiveData.getValue();
                            assert currentMovie != null;
                            currentMovie.addAll(upcomingMovieList);
                            upcomingMovieLiveData.postValue(currentMovie);

                        }
                    } else {
                        assert response.errorBody() != null;
                        System.out.println(response.errorBody().string());
                        upcomingMovieLiveData.postValue(null);
                    }
                } else {
                    System.out.println("Request isnt successful");
                    //response.message();
                }

            } catch (IOException e) {
                System.out.println(e);
                upcomingMovieLiveData.postValue(null);
            }
        }


        //method getPopularity
        private Call<UpcomingMovieResponses> getUpcomingMovie(int page){
            return ApiService.getMovieApi().getUpcomingMovie(Credentials.KEY, page);
        }
    }

}
