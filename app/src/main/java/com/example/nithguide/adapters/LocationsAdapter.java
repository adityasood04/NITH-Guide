package com.example.nithguide.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nithguide.R;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    Context context;
    ArrayList<Pair<String,Integer>> locations;
    Listener listener;


    public LocationsAdapter(Context context, ArrayList<Pair<String,Integer>> locations, Listener listener){
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
        String location = locations.get(position).first;
        holder.tvLocationName.setText(location);
        switch(locations.get(position).second){
            case -2 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.health));
                break;
            }
            case -1 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.library));
                break;
            }
            case 0 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.gate));
                break;
            }
            case 1 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.admin));
                break;
            }
            case 56 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.departments));
                break;
            }
            case 100 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.hostel));
                break;
            }
            case 200 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.park));
                break;
            }
            case 300 : {
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.ground));
                break;
            }
            default:{
                holder.ivLocationType.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.food));
                break;
            }

        }


        holder.tvLocationName.setOnClickListener(view -> {
            listener.onLocationClicked(locations.get(holder.getAdapterPosition()).first, holder.getAdapterPosition());
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
        TextView tvLocationName;
        ImageView ivLocationType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            ivLocationType = itemView.findViewById(R.id.ivLocation);

        }
    }


}