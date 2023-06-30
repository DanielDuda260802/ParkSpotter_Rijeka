package com.example.parkspotter_rijeka;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NedostupniPodaciParking extends AsyncTask<Void, Void, ArrayList<ModelClass>> {
    private ProgressDialog progressDialog;
    private ArrayList<ModelClass> parkingLive;
    private Context context;
    private Handler mainHandler;
    private RecyclerView recyclerView;

    public NedostupniPodaciParking(ArrayList<ModelClass> parkingLive, Context context, RecyclerView recyclerView) {
        this.parkingLive = parkingLive;
        this.context = context;
        this.recyclerView = recyclerView;
        mainHandler = new Handler();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Ostala parkirališta");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected ArrayList<ModelClass> doInBackground(Void... voids) {
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

            JSONArray parkingData_withLiveStatusFalse = new JSONArray();

            try {
                JSONArray jsonArray = new JSONArray(dataJSON);
                boolean live_status;

                for (int i = 1; i < jsonArray.length(); i++) {
                    JSONObject allParkingsData = jsonArray.getJSONObject(i);
                    JSONObject parking_data = allParkingsData.getJSONObject("parking_data");

                    try {
                        live_status = parking_data.optBoolean("live_status", false);
                        if (live_status == false) {
                            parkingData_withLiveStatusFalse.put(jsonArray.getJSONObject(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                parkingLive.clear();

                for (int i = 0; i < parkingData_withLiveStatusFalse.length(); i++) {
                    JSONObject parkingInstance = parkingData_withLiveStatusFalse.getJSONObject(i);
                    JSONObject parking_data = parkingInstance.getJSONObject("parking_data");

                    String parking_name = parkingInstance.getString("parking_name");
                    String status_sustava = parking_data.optString("status_sustava", "Nema podataka");
                    String link = parkingInstance.getString("link");
                    String kategorija = parkingInstance.getString("category");

                    ModelClass modelClass = new ModelClass(parking_name, status_sustava, 0, 0, link, kategorija);
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

        return parkingLive;
    }

    @Override
    protected void onPostExecute(ArrayList<ModelClass> result) {
        super.onPostExecute(result);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        // Update the adapter with the new data
        CustomAdapter adapter = new CustomAdapter(context, parkingLive);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

