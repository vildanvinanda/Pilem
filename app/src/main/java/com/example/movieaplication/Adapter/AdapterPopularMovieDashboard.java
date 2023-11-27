package com.example.movieaplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieaplication.Activity.DetailMovie;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.Credentials;
import com.example.movieaplication.ViewHolder.MovieViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPopularMovieDashboard extends RecyclerView.Adapter<MovieViewHolder> {


    Context context;
    private List<MovieModel> movieList;

    public static final String EXTRA_ID= "id";
    public static final String EXTRA_DES = "des";
    public static final String EXTRA_JUDUL = "judul";
    public static final String EXTRA_IMG = "img";
    public static final String EXTRA_TGL = "release";
    public static final String EXTRA_ORIGINAL_LANGUAGE = "language";
    public static final String EXTRA_RATE_VALUE = "rateValue";
    public static final String EXTRA_RATE = "rateText";

    public AdapterPopularMovieDashboard(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull MovieViewHolder holder, int position) {
        MovieModel model = movieList.get(position);
        String rating = String.valueOf(model.getVote_average());
        int maxLengthforRate = 3;
        int maxLengthforDate = 4;

        holder.title.setText(model.getTitle());
        holder.rate.setText(rating);
        holder.release.setText(model.getRelease_date());
        Glide.with(holder.img).load(Credentials.POSTER_URL+model.getPoster_path()).into(holder.img);

        if (rating.length() > maxLengthforRate) {
            String truncatedText = rating.substring(0, maxLengthforRate) + "";
            holder.rate.setText(truncatedText);
        } else {
            holder.rate.setText(rating);
        }

        if (model.getRelease_date().length() > maxLengthforDate) {
            String truncatedText = model.getRelease_date().substring(0, maxLengthforDate) + "";
            holder.release.setText(truncatedText);
        } else {
            holder.release.setText(model.getRelease_date());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(EXTRA_IMG, model.getPoster_path());
                intent.putExtra(EXTRA_JUDUL, model.getTitle());
                intent.putExtra(EXTRA_TGL, model.getRelease_date());
                intent.putExtra(EXTRA_DES, model.getOverview());
                intent.putExtra(EXTRA_ID, model.getId());
                intent.putExtra(EXTRA_RATE_VALUE, model); // Menggunakan Parcelable
                intent.putExtra(EXTRA_RATE, rating);
                intent.putExtra(EXTRA_ORIGINAL_LANGUAGE, model.getOriginal_language());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movieList != null){
            return movieList.size();
        } return 0;
    }
}
