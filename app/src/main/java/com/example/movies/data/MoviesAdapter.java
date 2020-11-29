package com.example.movies.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.activities.MainActivity2;
import com.example.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);

        String title = currentMovie.getTitle();
        String year = currentMovie.getYear();
        String posterUrl = currentMovie.getPosterUrl();

        holder.titleTextView.setText(title);
        holder.yearTextView.setText(year);

        Picasso.get().load(posterUrl).fit().centerInside().into(holder.posterImageView);
    }

    @Override
    //Возвращаем количество элиментов
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Параметры для доступа к разметке
        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;

        //Получаем доступ к разметке через itemView
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            TranslateAnimation animation = new TranslateAnimation(-200, 0, 0, 0);
            animation.setDuration(1000);
            animation.setFillAfter(true);
            posterImageView.startAnimation(animation);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);

            Intent intent = new Intent(context, MainActivity2.class);
            intent.putExtra("Name", movie.getTitle());
            intent.putExtra("Year", movie.getYear());
            intent.putExtra("image", movie.getPosterUrl());
            context.startActivity(intent);
        }
    }
}
