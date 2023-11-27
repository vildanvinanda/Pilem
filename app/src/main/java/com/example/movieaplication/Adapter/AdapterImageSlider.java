package com.example.movieaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movieaplication.Model.MovieModel;
import com.example.movieaplication.R;
import com.example.movieaplication.Utils.Credentials;
import com.example.movieaplication.ViewHolder.ImageSliderViewHolder;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AdapterImageSlider extends RecyclerView.Adapter<ImageSliderViewHolder>{

    Context context;
    private List<MovieModel> sliderItems;

    public AdapterImageSlider(Context context, List<MovieModel> sliderItems) {
        this.context = context;
        this.sliderItems = sliderItems;
    }

    @NotNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ImageSliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imgslider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ImageSliderViewHolder holder, int position) {
        MovieModel model = sliderItems.get(position);

        holder.title.setText(model.getTitle());
        Glide.with(holder.img).load(Credentials.POSTER_URL+model.getBackdrop_path()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        // Batasi jumlah item yang ditampilkan
        return sliderItems.size();
    }
}
