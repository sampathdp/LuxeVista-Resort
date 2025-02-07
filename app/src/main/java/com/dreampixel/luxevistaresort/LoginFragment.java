package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {

    private EditText emailField, passwordField;

    private DatabaseHelper  databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        TextView btn_signUp_Link = view.findViewById(R.id.registerLink);
        Button loginButton = view.findViewById(R.id.loginButton);

        emailField = view.findViewById(R.id.emailField);
        passwordField = view.findViewById(R.id.passwordField);

        loginButton.setOnClickListener(v->SignIn());
        btn_signUp_Link.setOnClickListener(v -> OpenSignUpPage());

        Slider(view);

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
    void OpenSignUpPage(){

        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer_Main, new RegistrationFragment());
            transaction.commit();
        }

    }

    void SignIn(){
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUserExists(email, password)) {

            User user = databaseHelper.getUserByEmail(email);
            int user_ID = 0;
            if (user != null)
                user_ID=user.getUser_id();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_email", email);
            editor.putInt("user_ID", user_ID);
            editor.apply();

            Intent intent = new Intent(this.getActivity(), HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}