package com.dreampixel.luxevistaresort;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.Arrays;
import java.util.List;

public class WelcomeFragment extends Fragment {
    private Button btnContinueActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Slider(view);
        OpenSignInPage(view);

        return view;
    }

    void Slider(View view){
        List<Integer> images = Arrays.asList(
                R.drawable.slider_image_1,
                R.drawable.slider_image_2,
                R.drawable.slider_image_3
        );

        ViewPager2 viewPager = view.findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(images);
        viewPager.setAdapter(adapter);
    }

    void OpenSignInPage(View view){
        btnContinueActivity = view.findViewById(R.id.continueButton);

        btnContinueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentContainer_Main, new LoginFragment());
                    transaction.commit();
                }
            }
        });
    }
}
