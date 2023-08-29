package com.example.parkspotter_rijeka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Button SlobodniParking_btn;
    private Button NepoznatoParking_btn;

    private ImageView logout;
    private ArrayList<ModelClass> parkingLive = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerviewId);
        SlobodniParking_btn = findViewById(R.id.SlobodnoParking);
        NepoznatoParking_btn = findViewById(R.id.OstaliParking);
        logout = findViewById(R.id.logout_icon);

        adapter = new CustomAdapter(MainActivity.this, parkingLive);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        SlobodniParking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlobodniParking fetchData = new SlobodniParking(parkingLive, MainActivity.this, recyclerView);
                fetchData.execute();
            }
        });

        NepoznatoParking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NedostupniPodaciParking nedostupniPodaciParking = new NedostupniPodaciParking(parkingLive, MainActivity.this, recyclerView);
                nedostupniPodaciParking.execute();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}



