package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class NearbyAttractionsFragment extends Fragment {

    private RecyclerView placesRecyclerView;
    private PlacesAdapter adapter;
    private List<Place> places;

    public NearbyAttractionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_attractions, container, false);

        placesRecyclerView = view.findViewById(R.id.recycler_view_attractions);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        places = new ArrayList<>();
        places.add(new Place("Galle Fort", "A historic fort with stunning views of the ocean.", "2.5 km away", 4.5f, R.drawable.slider_image_3));
        places.add(new Place("Mirissa Beach", "A popular beach for whale watching and surfing.", "1.5 km away", 4.7f, R.drawable.slider_image_3));
        places.add(new Place("Yala National Park", "A famous wildlife park known for leopard sightings.", "12.5 km away", 4.8f, R.drawable.slider_image_3));
        places.add(new Place("Unawatuna Beach", "A beautiful beach with calm waters and great for swimming.", "5.0 km away", 4.6f, R.drawable.slider_image_3));
        places.add(new Place("Hikkaduwa Beach", "A vibrant beach known for its coral reefs and snorkeling opportunities.", "8.0 km away", 4.4f, R.drawable.slider_image_3));
        places.add(new Place("Weligama Bay", "A popular spot for surfing and sandy beaches.", "6.5 km away", 4.5f, R.drawable.slider_image_3));


        adapter = new PlacesAdapter(places);
        placesRecyclerView.setAdapter(adapter);

        return view;
    }
}