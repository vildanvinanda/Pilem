package com.example.movieaplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.movieaplication.Adapter.MovieListAdapter;
import com.example.movieaplication.Model.Movie;
import com.example.movieaplication.Presenter.MainActivityPresenter;
import com.example.movieaplication.R;
import com.example.movieaplication.View.MovieContract;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieContract.View {

    private MovieContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
        presenter.loadMovieList();
    }

    @Override
    public void showMovieList(List<Movie> movies) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MovieListAdapter adapter = new MovieListAdapter(movies, position -> presenter.onMovieItemClick(position));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showMovieDetail(Movie movie) {
        // Implementasikan navigasi ke halaman detail movie
//        Intent intent = new Intent(this, MovieDetailActivity.class);
//        intent.putExtra("movie", movie);
//        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}