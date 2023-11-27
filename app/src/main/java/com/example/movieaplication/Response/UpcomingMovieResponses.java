package com.example.movieaplication.Response;

import com.example.movieaplication.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UpcomingMovieResponses {
    //kita akan mengambil result dari api popullar
    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

    //bikin method karena list<MovieModel> ini akan di tampilkan pada halaman yang lain
    public List<MovieModel> getUpcomingMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "UpcomingMovieResponses{" +
                "movies=" + movies +
                '}';
    }
}
