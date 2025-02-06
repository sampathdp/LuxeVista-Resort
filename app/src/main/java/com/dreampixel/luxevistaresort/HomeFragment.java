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
import androidx.viewpager2.widget.ViewPager2;

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
    private LinearLayout roomOffersContainer;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewSlider(view);

        roomOffersContainer = view.findViewById(R.id.roomOffersContainer);

        dbHelper = new DatabaseHelper(getContext());

        loadLatestRooms();


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



    private void loadLatestRooms() {
        List<Room> latestRooms = dbHelper.getLatestRooms(3);

        for (Room room : latestRooms) {
            View roomView = LayoutInflater.from(getContext()).inflate(R.layout.item_room_offer, roomOffersContainer, false);

            ImageView roomImage = roomView.findViewById(R.id.iv_room_image);
            TextView tvRoomType = roomView.findViewById(R.id.tv_room_type);
            TextView tvRoomDescription = roomView.findViewById(R.id.tv_room_description);
            TextView tvRoomPrice = roomView.findViewById(R.id.tv_room_price);
            Button btnBook = roomView.findViewById(R.id.btn_room_book);

            // Set Room Data
            tvRoomType.setText(room.getType());
            tvRoomDescription.setText(room.getDescription());
            tvRoomPrice.setText("Price: LKR " + room.getPricePerNight() + " per night");

            // Convert BLOB image to Bitmap
            if (room.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(room.getImage(), 0, room.getImage().length);
                roomImage.setImageBitmap(bitmap);
            } else {
                roomImage.setImageResource(R.drawable.slider_image_1); // Default image if null
            }
            roomOffersContainer.addView(roomView);
        }
    }

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