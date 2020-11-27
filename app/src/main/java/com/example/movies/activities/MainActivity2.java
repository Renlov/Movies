package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textView2 = findViewById(R.id.textViewActivityMain2);
        Intent intent = getIntent();
        if(intent != null) {
            textView2.setText(intent.getStringExtra("Name"));
        }

    }
    private void getMovie(){

    }
}