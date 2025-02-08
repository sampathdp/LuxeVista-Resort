package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MaterialCardView bookRoom = view.findViewById(R.id.card_book_room);
        MaterialCardView reserveServices = view.findViewById(R.id.card_reserve_service);
        MaterialCardView nearbyAttractionCard = view.findViewById(R.id.card_nearby_attractions);

        bookRoom.setOnClickListener(v->{
            ViewRooms();
        });
        reserveServices.setOnClickListener(v->{
            ViewServices();
        });
        nearbyAttractionCard.setOnClickListener(v->{
            ViewNerabyLocations();
        });

        recyclerView = view.findViewById(R.id.recyclerViewRooms);
        databaseHelper = new DatabaseHelper(getContext());

        ViewSlider(view);
        setupRecyclerView();

        return view;

    }



    private void setupRecyclerView() {
        List<Room> rooms = databaseHelper.getLatestRooms(1);
        RoomAdapter adapter = new RoomAdapter(getContext(),rooms);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    void ViewServices(){
        getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ServiceFragment())
                    .addToBackStack(null)
                    .commit();
    }
    public void ViewRooms() {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RoomSectionFragment())
                    .addToBackStack(null)
                    .commit();
    }
    private void ViewNerabyLocations() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NearbyAttractionsFragment())
                .addToBackStack(null)
                .commit();
    }

    void ViewSlider(View view){
        ViewPager2 featuredCarousel = view.findViewById(R.id.featuredCarousel);

        List<Integer> images = Arrays.asList(
                R.drawable.slider_image_1,
                R.drawable.slider_image_2,
                R.drawable.slider_image_3
        );

        CarouselAdapter adapter = new CarouselAdapter(images);
        featuredCarousel.setAdapter(adapter);

        featuredCarousel.setPageTransformer((page, position) -> {
            float absPos = Math.abs(position);
            page.setScaleY(1 - (0.25f * absPos));
            page.setAlpha(1 - (0.5f * absPos));
        });
    }

}