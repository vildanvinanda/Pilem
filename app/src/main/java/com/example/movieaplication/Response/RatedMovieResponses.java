package com.example.movieaplication.Response;

import com.example.movieaplication.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RatedMovieResponses {
    //kita akan mengambil result dari api rated movie
    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

    public List<MovieModel> getTopRatedMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "RatedMovieResponses{" +
                "movies=" + movies +
                '}';
    }
}
