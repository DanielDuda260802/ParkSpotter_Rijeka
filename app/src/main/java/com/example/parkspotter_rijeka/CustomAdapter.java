package com.example.parkspotter_rijeka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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

        String stasusSustava = currentData.getStatus_sustava();

        if(stasusSustava == "")
        {
            holder.parking_name.setText(currentData.getParkingName());
            holder.kategorija.setText(String.valueOf(currentData.getKategorija()));
            holder.kapacitet.setText(String.valueOf(currentData.getKapacitet()));
            holder.slobodno.setText(String.valueOf(currentData.getSlobodno()));
            holder.url.setText(String.valueOf(currentData.getUrl()));
        }
        else
        {
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

        TextView parking_name, status_sustava, kapacitet, slobodno, url, kategorija;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            parking_name = itemView.findViewById(R.id.parkingName);
            status_sustava = itemView.findViewById(R.id.StatusSustava);
            kapacitet = itemView.findViewById(R.id.kapacitet);
            slobodno = itemView.findViewById(R.id.slobodnaMjesta);
            url = itemView.findViewById(R.id.parkingURL);
            kategorija = itemView.findViewById(R.id.kategorija);
        }
    }
}

