package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView serviceTitle, serviceDateTime, countdownText;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MaterialCardView bookRoom = view.findViewById(R.id.card_book_room);
        MaterialCardView reserveServices = view.findViewById(R.id.card_reserve_service);
        MaterialCardView nearbyAttractionCard = view.findViewById(R.id.card_nearby_attractions);

        serviceTitle = view.findViewById(R.id.serviceTitle);
        serviceDateTime = view.findViewById(R.id.serviceDateTime);
        countdownText = view.findViewById(R.id.countdownText);

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
        loadLatestReservation();
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
                R.drawable.villa_1,
                R.drawable.g2,
                R.drawable.spa_massage,
                R.drawable.dining_romantic
        );

        CarouselAdapter adapter = new CarouselAdapter(images);
        featuredCarousel.setAdapter(adapter);

        featuredCarousel.setPageTransformer((page, position) -> {
            float absPos = Math.abs(position);
            page.setScaleY(1 - (0.25f * absPos));
            page.setAlpha(1 - (0.5f * absPos));
        });
    }

    private void loadLatestReservation() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_ID", 0);

        ServiceReservation latestReservation = databaseHelper.getLatestServiceReservation(userId);

        if (latestReservation != null) {
            serviceTitle.setText(latestReservation.getServiceName());
            serviceDateTime.setText(formatDateTime(latestReservation.getReservationDateTime()));
            countdownText.setText("Countdown: " + getTimeDifference(latestReservation.getReservationDateTime()));
        } else {
            serviceTitle.setText("No upcoming appointments");
            serviceDateTime.setText("");
            countdownText.setText("");
        }
    }


    private String formatDateTime(String dateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, h:mm a", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime;
        }
    }

    private String getTimeDifference(String reservationDateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date reservationDate = sdf.parse(reservationDateTime);
            long diff = reservationDate.getTime() - System.currentTimeMillis();

            if (diff > 0) {
                long hours = (diff / (1000 * 60 * 60)) % 24;
                long minutes = (diff / (1000 * 60)) % 60;
                return hours + " hours " + minutes + " minutes";
            } else {
                return "Appointment time passed";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Unknown time";
        }
    }

}