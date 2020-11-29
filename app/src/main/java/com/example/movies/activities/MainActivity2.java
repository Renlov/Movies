package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    private final String url = "http://www.omdbapi.com/?t=";
    private final String apiKey = "&apikey=1113844f";
    private RequestQueue mRequestQueue;
    ImageView imageView;
    TextView titleView;
    TextView yearView;
    TextView genreView;
    TextView directorView;
    TextView timeView;
    TextView plotView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.posterImageView);
        titleView = findViewById(R.id.titleTextView);
        yearView = findViewById(R.id.yearTextView);
        genreView = findViewById(R.id.genreTextView);
        directorView = findViewById(R.id.directorTextView);
        timeView = findViewById(R.id.timeTextView);
        plotView = findViewById(R.id.plotTextView);

        mRequestQueue = Volley.newRequestQueue(this);

        getMovies();

        TranslateAnimation animation = new TranslateAnimation(-200, 0, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    private void getMovies() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String textName = name.replace(" ", "+");
        String text = url + textName + apiKey;
        Log.d("text", text);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, text,
                 null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String poster = jsonObject.getString("Poster");
                    String title = jsonObject.getString("Title");
                    String year = jsonObject.getString("Year");
                    String genre = jsonObject.getString("Genre");
                    String director = jsonObject.getString("Director");
                    String time = jsonObject.getString("Runtime");
                    String plot = jsonObject.getString("Plot");
                    Picasso.get().load(poster).into(imageView);

                    titleView.setText(title);
                    yearView.setText(year);
                    genreView.setText(genre);
                    directorView.setText(director);
                    timeView.setText(time);
                    plotView.setText(plot);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //В случае ошибки вывидется стек ошибок
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

}