package com.example.covidinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WorldDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_data);

        //setting up the titlebar text
        getSupportActionBar().setTitle("CovidInfo (World)");
    }
}