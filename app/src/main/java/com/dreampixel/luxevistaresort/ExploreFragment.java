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
        imageResources.add(R.drawable.g1);
        imageResources.add(R.drawable.spa_ayurveda);
        imageResources.add(R.drawable.villa_3);
        imageResources.add(R.drawable.pool_cabana);
        imageResources.add(R.drawable.adventure_diving);
        imageResources.add(R.drawable.dining_romantic);
        imageResources.add(R.drawable.family_3);
        imageResources.add(R.drawable.g2);
        imageResources.add(R.drawable.pool_side_cabana_img);
        imageResources.add(R.drawable.dining_high_tea);
        imageResources.add(R.drawable.slider_image_2);
        imageResources.add(R.drawable.g4);
        imageResources.add(R.drawable.fine_dine_img);
        imageResources.add(R.drawable.dining_seafood);
        imageResources.add(R.drawable.g3);
        galleryAdapter = new GalleryAdapter(getContext(), imageResources);
        recyclerViewGallery.setAdapter(galleryAdapter);


        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    void CarouselView(View view){

        List<Integer> images = Arrays.asList(
                R.drawable.g2,
                R.drawable.g4,
                R.drawable.spa_ayurveda
        );

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerTour);
        SliderAdapter adapter = new SliderAdapter(images);
        viewPager.setAdapter(adapter);
    }
}
