package com.example.movieaplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieaplication.Adapter.AdapterPopularMovie;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.Credentials;
import com.example.movieaplication.databinding.ActivityDetailMovieBinding;

public class DetailMovie extends AppCompatActivity {

    private ActivityDetailMovieBinding binding;
    int maxLengthforDate = 4;
    int maxLengthforRate = 3;
    TextView[] dots;
    int dotSize = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String title = intent.getStringExtra(AdapterPopularMovie.EXTRA_JUDUL);
        String des = intent.getStringExtra(AdapterPopularMovie.EXTRA_DES);
        String date = intent.getStringExtra(AdapterPopularMovie.EXTRA_TGL);
        String img = intent.getStringExtra(AdapterPopularMovie.EXTRA_IMG);
        String rate = intent.getStringExtra(AdapterPopularMovie.EXTRA_RATE);
        String language = intent.getStringExtra(AdapterPopularMovie.EXTRA_ORIGINAL_LANGUAGE);

        binding.txtMovie.setText(title);
        binding.ratingTextDetail.setText(rate);
        binding.txtDeskripsi.setText(des);
        binding.txtLanguage.setText(language);
        Glide.with(binding.imgPoster)
                .load(Credentials.POSTER_URL+img)
                .centerCrop()
                .into(binding.imgPoster);

        if (date.length() > maxLengthforDate) {
            String truncatedText = date.substring(0, maxLengthforDate) + "";
            binding.txtDate.setText(truncatedText);
        } else {
            binding.txtDate.setText(date);
        }

        AllButton();

        // rating dots
        setUpRatingDots();
    }

    private void setUpRatingDots() {
        MovieModel movieModel = getIntent().getParcelableExtra(AdapterPopularMovie.EXTRA_RATE_VALUE);
        if (movieModel != null) {

            float rating = movieModel.getVote_average();

            // Mengonversi nilai float menjadi string dengan satu digit desimal
            String formattedRating = String.format("%.1f", rating);

            // Mengganti koma dengan titik
            formattedRating = formattedRating.replace(",", ".");

            float j = Float.parseFloat(formattedRating);

            dots = new TextView[dotSize];
            binding.starDetail.removeAllViews();

            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#9733;"));
                dots[i].setTextColor(this.getResources().getColor(R.color.dotsBg));
                dots[i].setTextSize(24);
                binding.starDetail.addView(dots[i]);
            }

            if (j >= 1.0f && j <= 1.9f){
                dots[0].setTextColor(this.getResources().getColor(R.color.dots_half));
            } else if (j >= 2.0f && j <= 2.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
            } else if (j >= 3.0f && j <= 3.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.dots_half));
            } else if (j >= 4.0f && j <= 4.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
            } else if (j >= 5.0f && j <= 5.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.dots_half));
            }else if (j >= 6.0f && j <= 6.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.material_blue));
            }else if (j >= 7.0f && j <= 7.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[3].setTextColor(this.getResources().getColor(R.color.dots_half));
            }else if (j >= 8.0f && j <= 8.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[3].setTextColor(this.getResources().getColor(R.color.material_blue));
            }else if (j >= 9.0f && j <= 9.9f) {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[3].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[4].setTextColor(this.getResources().getColor(R.color.dots_half));
            }else {
                dots[0].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[1].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[2].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[3].setTextColor(this.getResources().getColor(R.color.material_blue));
                dots[4].setTextColor(this.getResources().getColor(R.color.material_blue));
            }

        }
    }

    private void AllButton() {
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}