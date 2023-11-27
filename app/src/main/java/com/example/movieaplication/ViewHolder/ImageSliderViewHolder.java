package com.example.movieaplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieaplication.R;

import org.jetbrains.annotations.NotNull;

public class ImageSliderViewHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView title;

    public ImageSliderViewHolder(@NotNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);

    }

}
