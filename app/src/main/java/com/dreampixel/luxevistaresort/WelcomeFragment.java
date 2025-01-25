package com.dreampixel.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class WelcomeFragment extends Fragment {
    private Button buttonOpenActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        // Image list
        List<Integer> images = Arrays.asList(
                R.drawable.slider_image_1,
                R.drawable.slider_image_2,
                R.drawable.slider_image_3
        );

        // Set up slider
        ViewPager2 viewPager = view.findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(images);
        viewPager.setAdapter(adapter);


        // Find the button by its ID
        buttonOpenActivity = view.findViewById(R.id.continueButton);

        // Set click listener on the button
        buttonOpenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity when the button is clicked
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;


    }

}