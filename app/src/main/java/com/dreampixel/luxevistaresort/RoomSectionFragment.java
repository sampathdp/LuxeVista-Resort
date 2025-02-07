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

import java.util.List;


public class RoomSectionFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_section, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        databaseHelper = new DatabaseHelper(getContext());

        setupRecyclerView();

        return view;
    }
    private void setupRecyclerView() {
        List<Room> rooms = databaseHelper.getLatestRooms(0);
        RoomAdapter adapter = new RoomAdapter(rooms);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}