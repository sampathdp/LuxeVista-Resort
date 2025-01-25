package com.dreampixel.luxevistaresort;

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

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 featuredCarousel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewSlider(view);
        BookNow(view);

        return view;
    }

    // Navigate to BookNowFragment
    void BookNow(View view){
        // Set up button click
        Button bookNowButton = view.findViewById(R.id.btn_room_book);
        bookNowButton.setOnClickListener(v -> {
            // Open BookNowFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BookNowFragment()) // Replace with container ID
                    .addToBackStack(null)
                    .commit();
        });
    }

    // Initialize ViewPager2
    void ViewSlider(View view){
        featuredCarousel = view.findViewById(R.id.featuredCarousel);

        // Sample images for the slider
        List<Integer> images = Arrays.asList(
                R.drawable.slider_image_1,
                R.drawable.slider_image_2,
                R.drawable.slider_image_3
        );

        // Set up the adapter
        CarouselAdapter adapter = new CarouselAdapter(images);
        featuredCarousel.setAdapter(adapter);

        // Add smooth scaling effect
        featuredCarousel.setPageTransformer((page, position) -> {
            float absPos = Math.abs(position);
            page.setScaleY(1 - (0.25f * absPos));
            page.setAlpha(1 - (0.5f * absPos));
        });
    }

}