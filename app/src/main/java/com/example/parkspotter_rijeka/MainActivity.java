package com.example.parkspotter_rijeka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    CustomAdapter adapter;
    Button fetchDataBtn;

    ArrayList<ModelClass> parkingLive = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerviewId);
        fetchDataBtn = findViewById(R.id.fetchData);

        new fetchData().start();

        adapter = new CustomAdapter(MainActivity.this , parkingLive);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fetchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new fetchData().start();
                adapter.notifyDataSetChanged();
            }
        });



    }

    class fetchData extends Thread {
        @Override
        public void run() {
            super.run();

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Fetching data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            try {
                URL url = new URL("https://www.rijeka-plus.hr/wp-json/restAPI/v1/parkingAPI/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine(), dataJSON = "";



                while (line != null) {
                    dataJSON = dataJSON + line;
                    line = bufferedReader.readLine();
                }

                // ovo su svi parkinzi čiji je live_status = true
                JSONArray parkingDataLive = new JSONArray();

                try {
                    JSONArray jsonArray = new JSONArray(dataJSON);
                    boolean live_status;

                    for (int i = 1; i < jsonArray.length(); i++) {
                        // ovo su svi parkinzini iz JSONa osim prvog
                        JSONObject allParkingsData = jsonArray.getJSONObject(i);


                        JSONObject parking_data = allParkingsData.getJSONObject("parking_data");

                        try {
                            live_status = parking_data.getBoolean("live_status");
                        } catch (JSONException e) {
                            live_status = false;
                        }


                        if (live_status) parkingDataLive.put(jsonArray.getJSONObject(i));
                    }

                    parkingLive.clear();
                    for (int i = 0; i < parkingDataLive.length(); i++) {
                        JSONObject parkingInstance = parkingDataLive.getJSONObject(i);

                        JSONObject parking_data = parkingInstance.getJSONObject("parking_data");

                        String parking_name = parkingInstance.getString("parking_name");
                        int kapacitet = parking_data.getInt("kapacitet");
                        int slobodno = parking_data.getInt("slobodno");
                        String link = parkingInstance.getString("link");
                        String kategorija = parkingInstance.getString("category");

                        ModelClass modelClass = new ModelClass(parking_name, kapacitet, slobodno, link, kategorija);

                        parkingLive.add(modelClass);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing());
                    progressDialog.dismiss();
                }
            });
        }
    }


}

