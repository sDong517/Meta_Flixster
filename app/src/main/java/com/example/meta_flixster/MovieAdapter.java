package com.example.meta_flixster;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.meta_flixster.databinding.ItemMovieBinding;
import com.example.meta_flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    private ItemMovieBinding binding;

    //Param Constructor
    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }


    //Turns xml into what you actually can see
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    //populating the data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //get the movie at the position
        Movie movie = movies.get(position);
        //then bind the data into the viewholder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding itemMovieBinding;

        public ViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());
            this.itemMovieBinding = itemView;
        }

        public void bind(Movie movie) {
           itemMovieBinding.tvTitle.setText(movie.getTitle());
           itemMovieBinding.tvOverview.setText(movie.getOverview());

            String imageUrl;

            //instead of hardcoding portrait, we can make it conditionally

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            int radius = 150;

            Glide.with(context)
                    .load(imageUrl)
                    .transform(new RoundedCorners(radius))
                    .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .into(itemMovieBinding.ivPoster);

            //upon click, select the entire row instead. Then navigate into a new activity on tap
            itemMovieBinding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, movie.getTitle(),Toast.LENGTH_SHORT).show();                  //Use this to test if onclick works

                    //Intent 'i' will be used to create a new activity on container click
                    Intent i = new Intent(context, DetailActivity.class);

                    //i.putExtra("title",movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));

                    //allows for transitioning
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, (View)itemMovieBinding.ivPoster, "title_Transition");

                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
