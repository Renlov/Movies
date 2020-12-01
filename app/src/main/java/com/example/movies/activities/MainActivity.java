package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.MoviesAdapter;
import com.example.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;
    private String url = "http://www.omdbapi.com/?apikey=1113844f&s=";
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        //Для улечшения производительности
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMovie();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovie();
            }
        });
    }


    private void getMovies() {
        String content = editText.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url + content, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Распознаем JSON объект
                try {
                    JSONArray jsonArray = response.getJSONArray("Search");

                    for(int i = 0; i <jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String posterUrl = jsonObject.getString("Poster");

                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setPosterUrl(posterUrl);

                        movies.add(movie);

                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(moviesAdapter);


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

        requestQueue.add(request);
    }

    private class getMoviesAsyncTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            getMovies();
            return null;
        }
    }


    private void searchMovie(){
        movies.clear();

        //Убираем клавиатуру после нажатия на кнопку
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
        new getMoviesAsyncTask().execute();
    }
    }

}