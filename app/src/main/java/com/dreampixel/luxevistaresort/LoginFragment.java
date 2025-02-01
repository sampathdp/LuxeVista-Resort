package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {

    private Button btn_signUp_Link;

    private EditText emailField, passwordField;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Slider(view);
        OpenSignUpPage(view);
        SignIn(view);
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
    void OpenSignUpPage(View view){
        btn_signUp_Link = view.findViewById(R.id.registerLink);

        btn_signUp_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentContainer_Main, new RegistrationFragment());
                    transaction.commit();
                }
            }
        });
    }

    void SignIn(View view){
        dbHelper = new DatabaseHelper(getActivity());

        emailField = view.findViewById(R.id.emailField);
        passwordField = view.findViewById(R.id.passwordField);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (dbHelper.checkUserExists(email, password)) {
                // Proceed to next activity or fragment (e.g. HomePage)
                Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}