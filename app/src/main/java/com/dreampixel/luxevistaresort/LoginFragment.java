package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

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

        return view;
    }

}