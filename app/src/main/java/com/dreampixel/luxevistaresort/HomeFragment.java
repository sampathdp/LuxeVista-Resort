package com.dreampixel.luxevistaresort;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 featuredCarousel;
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private DatabaseHelper databaseHelper;
    private List<Room> roomList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper(getContext());
        roomList = databaseHelper.getLatestRooms();

        if (roomList.isEmpty()) {
            Log.e("RecyclerViewDebug", "No rooms found in database!");
        }

        roomAdapter = new RoomAdapter(getContext(), roomList);
        recyclerView.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();

        if (!roomList.isEmpty()) {
            roomAdapter.updateRoomList(roomList);
        }

        ViewSlider(view);

        return view;

    }

    // Navigate to BookNowFragment
//    void ViewBookNow(View view){
//
//        Button bookNowButton = view.findViewById(R.id.btn_room_book);
//        bookNowButton.setOnClickListener(v -> {
//
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new BookNowFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//    }
//    public void ViewRooms(View view) {
//        MaterialCardView bookRoomCard = view.findViewById(R.id.card_book_room);
//        bookRoomCard.setOnClickListener(v -> {
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new RoomSectionFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//    }


    void ViewSlider(View view){
        featuredCarousel = view.findViewById(R.id.featuredCarousel);

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