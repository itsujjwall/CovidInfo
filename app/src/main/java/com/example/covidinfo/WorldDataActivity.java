package com.example.covidinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Objects;

public class WorldDataActivity extends AppCompatActivity {

    //1
    TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new,
            tv_recovered, tv_recovered_new, tv_death, tv_death_new, tv_tests;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout lin_countrywise;
    PieChart pieChart;
    //2
    ProgressDialog progressDialog;
    //3
    String str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests;
    private int int_active_new = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_data);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //setting up the titlebar text
        getSupportActionBar().setTitle("CovidInfo (World)");


        //Initialise UI
        Init();

        //Fetch world's data
        FetchWorldData();


        swipeRefreshLayout.setOnRefreshListener(() -> {
            FetchWorldData();
            swipeRefreshLayout.setRefreshing(false);
            //Toast.makeText(MainActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
        });

        lin_countrywise.setOnClickListener(v -> {
            Toast.makeText(WorldDataActivity.this, "Country wise data", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(WorldDataActivity.this, CountryWiseDataActivity.class));
        });
    }



    public void ShowDialog(Context context) {
        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void DismissDialog() {
        progressDialog.dismiss();
    }

    private void FetchWorldData() {
        //show dialog
        ShowDialog(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://corona.lmao.ninja/v2/all";
        pieChart.clearChart();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Fetching data from API and storing into string
                        try {
                            str_confirmed = response.getString("cases");
                            str_confirmed_new = response.getString("todayCases");
                            str_active = response.getString("active");
                            str_recovered = response.getString("recovered");
                            str_recovered_new = response.getString("todayRecovered");
                            str_death = response.getString("deaths");
                            str_death_new = response.getString("todayDeaths");
                            str_tests = response.getString("tests");
                            Log.d("error", "onResponse: "+str_tests);

                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(() -> {
                                // setting up texted in the text view
                                tv_confirmed.setText(NumberFormat.getInstance().format(Long.parseLong(str_confirmed)));
                                tv_confirmed_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));

                                int_active_new = Integer.parseInt(str_confirmed_new)
                                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                                tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new));

                                tv_recovered.setText(NumberFormat.getInstance().format(Long.parseLong(str_recovered)));
                                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

                                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                                tv_tests.setText(NumberFormat.getInstance().format(Long.parseLong(str_tests)));//1 testing done in large number so int cannot handle

                                pieChart.addPieSlice(new PieModel("Active", Long.parseLong(str_active), Color.parseColor("#007afe")));
                                pieChart.addPieSlice(new PieModel("Recovered", Long.parseLong(str_recovered), Color.parseColor("#08a045")));
                                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));

                                pieChart.startAnimation();

                                DismissDialog();

                            },1000);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {

                });
        requestQueue.add(jsonObjectRequest);
    }

    private void Init() {
        tv_confirmed = findViewById(R.id.activity_world_data_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_world_data_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_world_data_active_textView);
        tv_active_new = findViewById(R.id.activity_world_data_active_new_textView);
        tv_recovered = findViewById(R.id.activity_world_data_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_world_data_recovered_new_textView);
        tv_death = findViewById(R.id.activity_world_data_death_textView);
        tv_death_new = findViewById(R.id.activity_world_data_death_new_textView);
        tv_tests = findViewById(R.id.activity_world_data_tests_textview);
        swipeRefreshLayout = findViewById(R.id.activity_world_data_swipe_refresh_layout);
        pieChart = findViewById(R.id.activity_world_data_piechart);
        lin_countrywise = findViewById(R.id.activity_world_data_countrywise_lin);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            Handler delayToshowProgress = new Handler();
            delayToshowProgress.postDelayed(() -> finish(),400);
        }
        return super.onOptionsItemSelected(item);
    }
}