package com.example.movies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;
    final String url = "http://www.omdbapi.com/?apikey=1113844f&s=";
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        //Для улечшения производительности
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);


        //При переходе из StartActivity - попадаем сюда и сразу набираем текст с клавиатуры
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMovies();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies();
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
                    doToast("Your movie not found");
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
        protected Void doInBackground(Void... voids) {
            getMovies();
            return null;
        }
    }


    private void searchMovies(){
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        movies.clear();
        //Убираем клавиатуру после нажатия на кнопку
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
        new getMoviesAsyncTask().execute();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_baseline_visibility_off_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            

          /*  final int DIRECTION_RIGHT = 1;
            final int DIRECTION_LEFT = 0;

            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive){

                int direction = dX > 0 ? DIRECTION_RIGHT : DIRECTION_LEFT;

                switch (direction) {
                    case DIRECTION_LEFT :
                        View itemView = viewHolder.itemView;
                        ColorDrawable bg = new ColorDrawable();


                        bg.setColor(Color.RED);
                        bg.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        bg.draw(c);

                        break;
                }
            }*/
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            movies.remove(viewHolder.getAdapterPosition());
            moviesAdapter.notifyDataSetChanged();
            doToast("Deleted");
        }
    };

    private void doToast(String text){
        Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    //Значек звезды для перехода в FavoriteActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favotite_movies, menu);
        return true;
    }

    //Переход в FavoriteActivity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.favoriteMovies){
            Intent openFavoriteMovies = new Intent(this, FavoriteActivity.class);
            startActivity(openFavoriteMovies);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

