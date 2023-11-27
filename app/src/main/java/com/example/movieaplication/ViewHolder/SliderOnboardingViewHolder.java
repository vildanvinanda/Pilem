package com.example.movieaplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieaplication.R;

import org.jetbrains.annotations.NotNull;

public class SliderOnboardingViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPoster;

    public SliderOnboardingViewHolder(@NotNull View itemView) {
        super(itemView);
        imgPoster = itemView.findViewById(R.id.imgPoster);
    }
}
