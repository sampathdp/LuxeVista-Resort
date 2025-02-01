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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class RegistrationFragment extends Fragment {

    private Button btn_signInLink;
    private EditText fullNameField, emailField, passwordField;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        OpenSignUpPage(view);
        SignUp(view);
        return view;
    }

    void OpenSignUpPage(View view){
        btn_signInLink = view.findViewById(R.id.btn_login_redirect);

        btn_signInLink.setOnClickListener(new View.OnClickListener() {
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

    void SignUp(View view){
        dbHelper = new DatabaseHelper(getActivity());

        fullNameField = view.findViewById(R.id.et_full_name);
        emailField = view.findViewById(R.id.et_email);
        passwordField = view.findViewById(R.id.et_password);
        registerButton = view.findViewById(R.id.btn_login_redirect);

        registerButton.setOnClickListener(v -> {
            String fullName = fullNameField.getText().toString();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            boolean isInserted = dbHelper.insertUser(fullName, email, password);
            if (isInserted) {
                Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
                // Redirect to login fragment or another page
            } else {
                Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}