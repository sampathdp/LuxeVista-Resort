package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

    public class RoomSectionFragment extends Fragment {
        private RecyclerView recyclerView;
        private RoomAdapter roomAdapter;
        private List<Room> allRooms;
        private List<Room> filteredRooms;
        private ChipGroup filterChipGroup;
        private Spinner priceSortSpinner;
        private SearchView searchView;
        private DatabaseHelper databaseHelper;

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_room_section, container, false);

            databaseHelper = new DatabaseHelper(getContext());

            recyclerView = view.findViewById(R.id.recycler_view);
            filterChipGroup = view.findViewById(R.id.filterChipGroup);
            priceSortSpinner = view.findViewById(R.id.priceSort);
            searchView = view.findViewById(R.id.searchView);

            // Sample data, should be replaced by actual data retrieval
            allRooms = getRoomsFromDatabase();
            filteredRooms = new ArrayList<>(allRooms);

            // Set up RecyclerView
            roomAdapter = new RoomAdapter(getContext(), filteredRooms);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(roomAdapter);

            // Set up filter listener
            filterChipGroup.setOnCheckedChangeListener((group, checkedId) -> applyFilters(view));

            // Set up price sort listener
            priceSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    sortRoomsByPrice(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });

            // Set up search view listener
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterRooms(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterRooms(newText);
                    return true;
                }
            });

            return view;
        }

        private List<Room> getRoomsFromDatabase() {
            return databaseHelper.getLatestRooms(0);
        }

        // Method to filter rooms based on selected chip and search query
        private void applyFilters(View view) {
            String selectedRoomType = getSelectedRoomType(view);

            filteredRooms.clear();
            for (Room room : allRooms) {
                if (roomMatchesFilters(room, selectedRoomType)) {
                    filteredRooms.add(room);
                }
            }

            filterRooms(searchView.getQuery().toString());  // Reapply the search filter after chip selection
            roomAdapter.notifyDataSetChanged();
        }

        // Check if the room matches the selected filter
        private boolean roomMatchesFilters(Room room, String roomType) {
            return (roomType.equals("All Rooms") || room.getType().equals(roomType));
        }

        // Get selected room type from chip group
        private String getSelectedRoomType(View view) {
            int selectedChipId = filterChipGroup.getCheckedChipId();
            Chip selectedChip = view.findViewById(selectedChipId);
            return selectedChip != null ? selectedChip.getText().toString() : "All Rooms";
        }

        // Method to filter rooms based on search query
        private void filterRooms(String query) {
            filteredRooms.clear();
            for (Room room : allRooms) {
                if (room.getType().toLowerCase().contains(query.toLowerCase()) ||
                        room.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredRooms.add(room);
                }
            }
            roomAdapter.notifyDataSetChanged();
        }

        // Sort rooms based on the price selection
        private void sortRoomsByPrice(int sortOption) {
            switch (sortOption) {
                case 0: // Sort by low to high price
                    Collections.sort(filteredRooms, Comparator.comparingDouble(Room::getPrice));
                    break;
                case 1: // Sort by high to low price
                    Collections.sort(filteredRooms, (room1, room2) -> Double.compare(room2.getPrice(), room1.getPrice()));
                    break;
            }
            roomAdapter.notifyDataSetChanged();
        }

        // Clean up resources to save memory
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            // Clear the filtered rooms and room adapter
            if (roomAdapter != null) {
                roomAdapter = null;
            }
            if (filteredRooms != null) {
                filteredRooms.clear();
                filteredRooms = null;
            }
        }
    }