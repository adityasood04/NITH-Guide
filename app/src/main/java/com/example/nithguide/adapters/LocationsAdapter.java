package com.example.nithguide.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nithguide.R;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    Context context;
    ArrayList<String> locations;
    Listener listener;


    public LocationsAdapter(Context context, ArrayList<String> locations, Listener listener){
        this.context = context;
        this.locations = locations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.btn_item,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String location = locations.get(position);
        holder.btnLocation.setText(location);
        holder.btnLocation.setOnClickListener(view -> {
            listener.onLocationClicked(locations.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public interface Listener{
        void onLocationClicked(String locationName, int position);
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btnLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLocation = itemView.findViewById(R.id.btnLocation);

        }
    }


}