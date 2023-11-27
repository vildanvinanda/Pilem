package com.example.movieaplication.Utils;

import com.example.movieaplication.Response.PopularMovieResponses;
import com.example.movieaplication.Response.RatedMovieResponses;
import com.example.movieaplication.Response.SearchMovieResponses;
import com.example.movieaplication.Response.SliderImageMovieResponses;
import com.example.movieaplication.Response.UpcomingMovieResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    //popular movie
    @GET("movie/popular")
    Call<PopularMovieResponses> getPopularMovie(
            @Query("api_key") String key,
            @Query("page") int page);

    //search popular movie
    @GET("search/movie")
    Call<SearchMovieResponses> getSearchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page);

    //now playing movie
    @GET("movie/now_playing")
    Call<SliderImageMovieResponses> getSliderImageMovie(
            @Query("api_key") String key,
            @Query("page") int page);

    //upcoming movie
    @GET("movie/upcoming")
    Call<UpcomingMovieResponses> getUpcomingMovie(
            @Query("api_key") String key,
            @Query("page") int page);

    //top rate movie
    @GET("movie/top_rated")
    Call<RatedMovieResponses> getTopRatedMovie(
            @Query("api_key") String key,
            @Query("page") int page);
}
