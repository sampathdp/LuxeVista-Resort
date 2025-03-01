package com.dreampixel.luxevistaresort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoomSectionFragment extends Fragment {
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> allRooms;
    private List<Room> filteredRooms;
    private Spinner roomTypeSpinner, priceSortSpinner;
    private SearchView searchView;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_section, container, false);

        databaseHelper = new DatabaseHelper(requireContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        roomTypeSpinner = view.findViewById(R.id.roomTypeSpinner);
        priceSortSpinner = view.findViewById(R.id.priceSort);
        searchView = view.findViewById(R.id.searchView);

        allRooms = getRoomsFromDatabase();
        filteredRooms = new ArrayList<>(allRooms);

        roomAdapter = new RoomAdapter(requireContext(), filteredRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(roomAdapter);

        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        priceSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                applySorting();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilters();
                return true;
            }
        });

        return view;
    }

    private List<Room> getRoomsFromDatabase() {
        return databaseHelper.getLatestRooms(0);
    }

    private void applyFilters() {
        String selectedRoomType = getSelectedRoomType();
        String searchQuery = searchView.getQuery().toString().toLowerCase();

        filteredRooms.clear();
        for (Room room : allRooms) {
            boolean roomTypeMatch = selectedRoomType.equals("All Rooms") || room.getType().equalsIgnoreCase(selectedRoomType);
            boolean searchMatch = searchQuery.isEmpty() || room.getType().toLowerCase().contains(searchQuery) || room.getDescription().toLowerCase().contains(searchQuery);

            if (roomTypeMatch && searchMatch) {
                filteredRooms.add(room);
            }
        }
        applySorting();
    }

    private String getSelectedRoomType() {
        return roomTypeSpinner.getSelectedItem().toString();
    }

    private void applySorting() {
        int sortOption = priceSortSpinner.getSelectedItemPosition();
        if (sortOption == 1) {
            Collections.sort(filteredRooms, Comparator.comparingDouble(Room::getPrice));
        } else if (sortOption == 2) {
            Collections.sort(filteredRooms, (room1, room2) -> Double.compare(room2.getPrice(), room1.getPrice()));
        }
        roomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        roomAdapter = null;
        filteredRooms = null;
    }
}
