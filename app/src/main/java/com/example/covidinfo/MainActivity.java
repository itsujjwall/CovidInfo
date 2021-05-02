package com.example.covidinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //1
    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_recovered, tv_recovered_new, tv_death,
            tv_death_new, tv_tests, tv_tests_new, tv_date, tv_time;
    //2
    private LinearLayout lin_state_data, lin_world_data;
    //3
    private SwipeRefreshLayout swipeRefreshLayout;
    private PieChart pieChart;
    //4


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise
        init();

        //Fetch Data from Api
        FetchData();
    }



    private void FetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://api.covid19india.org/data.json";

        pieChart.clearChart();

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }



    private void init() {
        tv_confirmed = findViewById(R.id.activity_main_confirmed_textview);
        tv_confirmed_new = findViewById(R.id.activity_main_confirmed_new_textview);
        tv_active = findViewById(R.id.activity_main_active_textview);
        tv_active_new = findViewById(R.id.activity_main_active_new_textview);
        tv_recovered = findViewById(R.id.activity_main_recovered_textview);
        tv_recovered_new = findViewById(R.id.activity_main_recovered_new_textview);
        tv_death = findViewById(R.id.activity_main_death_textview);
        tv_death_new = findViewById(R.id.activity_main_death_new_textview);
        tv_tests = findViewById(R.id.activity_main_samples_textview);
        tv_tests_new = findViewById(R.id.activity_main_smaples_new_textview);
        tv_date = findViewById(R.id.activity_main_date_textview);
        tv_time = findViewById(R.id.activity_main_time_textview);

        pieChart = findViewById(R.id.activity_main_piechart);
        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater .inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_about){
            Toast.makeText(this, "About menu icon Clicked", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.menu_state_wise){
            Toast.makeText(this, "StateWise Data Clicked", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.menu_country_wise){
            Toast.makeText(this, "Country Wise Data Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}