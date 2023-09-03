package com.example.parkspotter_rijeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;



import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ModelClass> mData;

    public CustomAdapter(Context mContext, ArrayList<ModelClass> mData) {
        this.context = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);

        return new CustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelClass currentData = mData.get(position);
        holder.parking_name.setText(currentData.getParkingName());
        holder.status_sustava.setText(String.valueOf(currentData.getStatus_sustava()));
        holder.kapacitet.setText(String.valueOf(currentData.getKapacitet()));
        holder.slobodno.setText(String.valueOf(currentData.getSlobodno()));
        holder.url.setText(String.valueOf(currentData.getUrl()));
        holder.kategorija.setText(String.valueOf(currentData.getKategorija()));
        String url = currentData.getUrl();
        holder.Detalji_btn.setTag(url); // pohrana URL u button view

        String stasusSustava = currentData.getStatus_sustava();

        if (stasusSustava == "") {
            holder.parking_name.setText(currentData.getParkingName());
            holder.kategorija.setText(String.valueOf(currentData.getKategorija()));
            holder.kapacitet.setText(String.valueOf(currentData.getKapacitet()));
            holder.slobodno.setText(String.valueOf(currentData.getSlobodno()));
            holder.url.setText(String.valueOf(currentData.getUrl()));
        } else {
            holder.parking_name.setText(currentData.getParkingName());
            holder.kategorija.setText(String.valueOf(currentData.getKategorija()));
            holder.kapacitet.setText("Nedostupni podaci");
            holder.slobodno.setText("Nedostupni podaci");
            holder.url.setText(String.valueOf(currentData.getUrl()));
            holder.status_sustava.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        Button Detalji_btn;
        TextView parking_name, status_sustava, kapacitet, slobodno, url, kategorija;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Detalji_btn = itemView.findViewById(R.id.Detalji);
            parking_name = itemView.findViewById(R.id.parkingName);
            status_sustava = itemView.findViewById(R.id.StatusSustava);
            kapacitet = itemView.findViewById(R.id.kapacitet);
            slobodno = itemView.findViewById(R.id.slobodnaMjesta);
            url = itemView.findViewById(R.id.parkingURL);
            kategorija = itemView.findViewById(R.id.kategorija);

            Detalji_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dohvat URL-a
                    String url = (String) Detalji_btn.getTag(); // Ovdje promijenjeno Detalji_btn

                    // Provjera jesu li URL i dalje dostupni
                    if (!url.isEmpty()) {
                        // Otvaranje URL-a u web pregledniku
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        itemView.getContext().startActivity(intent); // Korištenje itemView.getContext()
                    } else {
                        Toast.makeText(itemView.getContext(), "URL nije dostupan.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

