package com.example.covidinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openGit(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/itsujjwall")));
    }

    public void openInsta(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/itsujjwall/")));
    }

    public void openTwitter(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/UjjwallChauhan")));
    }

    public void openFacebook(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ujjwal159357/")));
    }
}