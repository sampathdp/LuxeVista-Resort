package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class BookingHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingHistoryAdapter adapter;
    private List<BookingHistoryItem> bookingList;
    private DatabaseHelper dbHelper;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_ID", 1);

        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());

        try {
            bookingList = dbHelper.getUserBookingHistory(userId);
            adapter = new BookingHistoryAdapter(bookingList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error fetching data", e);
            Toast.makeText(getContext(), "Error loading bookings", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}