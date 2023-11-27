package com.example.movieaplication.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.Response.SliderImageMovieResponses;
import com.example.movieaplication.Utils.AppExecutor;
import com.example.movieaplication.Utils.Credentials;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Response;

public class SliderImageMovieApiClient {
    private static SliderImageMovieApiClient instance;

    public static SliderImageMovieApiClient getInstance(){
        if (instance == null){
            instance = new SliderImageMovieApiClient();
        }
        return instance;
    }

    //Live Data
    private MutableLiveData<List<MovieModel>> sliderimageMovieLiveData;

    //global variable runnable
    private SliderImageRunnable sliderimageRunnable;

    private SliderImageMovieApiClient() {
        sliderimageMovieLiveData = new MutableLiveData<>();
    }

    //kita akan nge parsing dulu dari popular movie
    public LiveData<List<MovieModel>> getSliderImageMovie(){
        return sliderimageMovieLiveData;
    }

    public void getSliderImageMovie(int page) {
        if (sliderimageRunnable != null){
            sliderimageRunnable = null;
        }

        sliderimageRunnable = new SliderImageRunnable(page);
        final Future handler = AppExecutor.getInstance().getNetworkID().submit(sliderimageRunnable);

        AppExecutor.getInstance().getNetworkID().schedule(() -> {
            //cancelling retrofit call
            handler.cancel(true);
        },3000, TimeUnit.MILLISECONDS);
    }

    //retriview data form rest API using runnable
    private class SliderImageRunnable implements Runnable {

        private final int page;
        boolean cancleRequest;

        public SliderImageRunnable(int page){
            this.page = page;
            cancleRequest = false;
        }

        @Override
        public void run(){
            try {
                Response response = getSliderImageMovie(page).execute();

                if (cancleRequest){
                    return;
                }

                if (response.isSuccessful()){
                    //retriview data
                    //jadi ketika response ini sukses akan tampil 200-299 itu sukses
                    if (response.code() == 200){
                        assert  response.body() != null;
                        List<MovieModel> sliderimageMovieList = new ArrayList<>(((SliderImageMovieResponses)response.body()).getMovies());

                        //kita cek jika page sama dengan 1 maka dia akan menampikan popular movie live data sedangkan
                        //jika tidak maka dia akan menampilkan list movie model

                        if (page == 1) {
                            sliderimageMovieLiveData.postValue(sliderimageMovieList);
                        } else {
                            List<MovieModel> currentMovie = sliderimageMovieLiveData.getValue();
                            assert currentMovie != null;
                            currentMovie.addAll(sliderimageMovieList);
                            sliderimageMovieLiveData.postValue(currentMovie);
                        }
                    } else {
                        assert response.errorBody() != null;
                        System.out.println(response.errorBody().string());
                        sliderimageMovieLiveData.postValue(null);
                    }
                } else {
                    System.out.println("Request isnt successful");
                    //response.message();
                }

            } catch (IOException e) {
                System.out.println(e);
                sliderimageMovieLiveData.postValue(null);
            }
        }

        //method getPopularity
        private Call<SliderImageMovieResponses> getSliderImageMovie(int page){
            return ApiService.getMovieApi().getSliderImageMovie(Credentials.KEY, page);
        }
    }

}
