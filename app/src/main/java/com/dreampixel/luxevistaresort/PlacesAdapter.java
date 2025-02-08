package com.dreampixel.luxevistaresort;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {
    private List<Place> places;

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, descriptionText, distanceText, ratingText;
        ImageView imageView;
        RatingBar ratingBar;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.attractionName);
            descriptionText = itemView.findViewById(R.id.attractionDescription);
            distanceText = itemView.findViewById(R.id.distanceChip);
            ratingText = itemView.findViewById(R.id.ratingText);
            imageView = itemView.findViewById(R.id.attractionImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    public PlacesAdapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nearby_attraction, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place place = places.get(position);
        holder.nameText.setText(place.name);
        holder.descriptionText.setText(place.description);
        holder.distanceText.setText(place.distance);
        holder.ratingText.setText(String.format("%s (2.1k)", place.rating));
        holder.ratingBar.setRating(place.rating);
        holder.imageView.setImageResource(place.imageResId);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}