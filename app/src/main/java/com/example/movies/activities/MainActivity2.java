package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.model.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    //private final String url = "http://www.omdbapi.com/?t=";
    //private final String apiKey = "&apikey=1113844f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ImageView imageView = findViewById(R.id.posterImageView);
        TextView titleView = findViewById(R.id.titleTextView);
        TextView yearView = findViewById(R.id.yearTextView);

        Intent intent = getIntent();
        if (intent != null) {

            titleView.setText(intent.getStringExtra("Name"));
            yearView.setText(intent.getStringExtra("Year"));


            //getMovie();
        }


        //protected void getMovie() {
        //   Intent intent = getIntent();
        //    String name = intent.getStringExtra("Name");
        //   String textName = name.replace(" ", "+");
        //   String text = url + textName + apiKey;

        //  }
    }
}

