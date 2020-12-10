package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.movies.R;
import com.example.movies.data.MoviesAdapter;

import java.util.ArrayList;


public class FavoriteActivity extends AppCompatActivity {

    private ListView listView;
    public ArrayList<String> favoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        listView = (ListView)findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.favorite_item, favoriteMovies);
        listView.setAdapter(arrayAdapter);


    }
}