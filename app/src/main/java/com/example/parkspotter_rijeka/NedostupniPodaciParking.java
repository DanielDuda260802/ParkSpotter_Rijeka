package com.example.parkspotter_rijeka;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

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
public class NedostupniPodaciParking {
    private ProgressDialog progressDialog;
    private ArrayList<ModelClass> parkingLive;
    private Handler mainHandler;

    public NedostupniPodaciParking(ArrayList<ModelClass> parkingLive) {
        this.parkingLive = parkingLive;
        mainHandler = new Handler();
    }

    public void fetchData(Context context) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Ostala parkirališta");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    // dataJSON --> pohranjeni svi podaci o parkiralištima

                    JSONArray parkingData_withLiveStatusFalse = new JSONArray();

                    try {
                        JSONArray jsonArray = new JSONArray(dataJSON);
                        boolean live_status;

                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject allParkingsData = jsonArray.getJSONObject(i); // dohvaćanje objekta parkirališta
                            JSONObject parking_data = allParkingsData.getJSONObject("parking_data");

                            try {
                                live_status = parking_data.optBoolean("live_status",false);
                                if (live_status == false) {
                                    parkingData_withLiveStatusFalse.put(jsonArray.getJSONObject(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // parkingData_withLiveStatusFalse --> svi objekti parkinga s live_status false
                        }

                        parkingLive.clear();

                        for (int i = 0; i < parkingData_withLiveStatusFalse.length(); i++) {
                            JSONObject parkingInstance = parkingData_withLiveStatusFalse.getJSONObject(i);
                            JSONObject parking_data = parkingInstance.getJSONObject("parking_data");

                            String parking_name = parkingInstance.getString("parking_name");
                            String status_sustava = parking_data.optString("status_sustava", "Nema podataka");
                            String link = parkingInstance.getString("link");
                            String kategorija = parkingInstance.getString("category");

                            ModelClass modelClass = new ModelClass(parking_name, status_sustava, 0,0, link, kategorija);
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
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }).start();
    }
}
