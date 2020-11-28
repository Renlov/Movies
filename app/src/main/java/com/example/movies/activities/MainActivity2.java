package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private final String url = "http://www.omdbapi.com/?t=";
    private final String apiKey = "&apikey=1113844f";
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();

        String name = intent.getStringExtra("Name");
        String textName = name.replace(" ", "+");
        String text = url + textName + apiKey;

        TextView textView = findViewById(R.id.titleTextView);
        textView.setText(text);
        getMovie();
    }


    private void getMovie() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String textName = name.replace(" ", "+");
        String text = url + textName + apiKey;


    }
}
