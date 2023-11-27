package com.example.movieaplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieaplication.Activity.DetailMovie;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.Credentials;
import com.example.movieaplication.ViewHolder.SliderOnboardingViewHolder;
import com.example.movieaplication.ViewHolder.UpcomingViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterTopRatedMovie extends RecyclerView.Adapter<SliderOnboardingViewHolder> {

    Context context;
    private List<MovieModel> movieList;

    public AdapterTopRatedMovie(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }
    public void updateData(List<MovieModel> newData) {
        this.movieList = newData;
        notifyDataSetChanged();
    }
    @NotNull
    @Override
    public SliderOnboardingViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new SliderOnboardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_onboarding, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull SliderOnboardingViewHolder holder, int position) {
        MovieModel model = movieList.get(position);
        Glide.with(holder.imgPoster).load(Credentials.POSTER_URL+model.getPoster_path()).centerCrop().into(holder.imgPoster);

        // Menghitung tinggi lapisan (50% dari tinggi holder.imgPoster)
        int gradientLayerHeight = holder.imgPoster.getHeight() / 4;

        // Membuat gradient drawable
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{0xFF1B1B1F, 0x1B1B1F});

        // Menetapkan tinggi lapisan gradient
        gradientDrawable.setBounds(0, holder.imgPoster.getHeight() - gradientLayerHeight, holder.imgPoster.getWidth(), holder.imgPoster.getHeight());

        // Menetapkan lapisan gradient drawable di bagian bawah gambar
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gradientDrawable});
        holder.imgPoster.setForeground(layerDrawable);
    }

    @Override
    public int getItemCount() {
        if(movieList != null){
            return movieList.size();
        } return 0;
    }
}
