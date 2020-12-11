package com.example.movies.data;

import android.app.Application;

import java.util.ArrayList;

public class ArrayMovie extends Application {

    private ArrayList<String> mGlobalVarValue;

    public ArrayList<String> getmGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setmGlobalVarValue(ArrayList<String> mGlobalVarValue) {
        this.mGlobalVarValue = mGlobalVarValue;
    }
}