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
        holder.parking_name.setText(mData.get(position).getParkingName());
        holder.kapacitet.setText(String.valueOf(mData.get(position).getKapacitet()));
        holder.slobodno.setText(String.valueOf(mData.get(position).getSlobodno()));
        holder.url.setText(String.valueOf(mData.get(position).getUrl()));
        holder.kategorija.setText(String.valueOf(mData.get(position).getKategorija()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView parking_name, kapacitet, slobodno, url, kategorija;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            parking_name = itemView.findViewById(R.id.parkingName);
            kapacitet = itemView.findViewById(R.id.kapacitet);
            slobodno = itemView.findViewById(R.id.slobodnaMjesta);
            url = itemView.findViewById(R.id.parkingURL);
            kategorija = itemView.findViewById(R.id.kategorija);
        }
    }
}

