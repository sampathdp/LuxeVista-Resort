package com.dreampixel.luxevistaresort;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreFragment extends Fragment {
    private RecyclerView recyclerViewGallery;
    private GalleryAdapter galleryAdapter;
    private List<Integer> imageResources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        GalleryView(view);
        CarouselView(view);

        return view;
    }
    void GalleryView(View view){

        recyclerViewGallery = view.findViewById(R.id.recyclerViewGallery);

        imageResources = new ArrayList<>();
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        imageResources.add(R.drawable.slider_image_1);
        galleryAdapter = new GalleryAdapter(getContext(), imageResources);
        recyclerViewGallery.setAdapter(galleryAdapter);


        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    void CarouselView(View view){

        List<Integer> images = Arrays.asList(
                R.drawable.slider_image_1,
                R.drawable.slider_image_2,
                R.drawable.slider_image_3
        );

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerTour);
        SliderAdapter adapter = new SliderAdapter(images);
        viewPager.setAdapter(adapter);
    }
}
