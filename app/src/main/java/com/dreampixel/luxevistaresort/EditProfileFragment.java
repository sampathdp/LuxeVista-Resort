package com.dreampixel.luxevistaresort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;


public class EditProfileFragment extends Fragment {
    private EditText etFullName, etTelephone, etPassword;
    private ImageView ivProfileImage;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private byte[] profileImage;
    private String currentEmail;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z]).{6,}$");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        etFullName = view.findViewById(R.id.et_full_name);
        etTelephone = view.findViewById(R.id.et_telephone);
        etPassword = view.findViewById(R.id.et_password);
        ivProfileImage = view.findViewById(R.id.iv_profile_picture);
        btnSave = view.findViewById(R.id.btn_save);

        databaseHelper = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentEmail = sharedPreferences.getString("user_email", "");

        loadUserDetails();

        ivProfileImage.setOnClickListener(v -> openImagePicker());

        btnSave.setOnClickListener(v -> saveUserDetails());

    return view;
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                ivProfileImage.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                profileImage = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadUserDetails() {
        if (!currentEmail.isEmpty()) {
            User user = databaseHelper.getUserByEmail(currentEmail);
            if (user != null) {
                etFullName.setText(user.getFullName());
                etTelephone.setText(user.getContact());
                etPassword.setText(user.getPassword());

                if (user.getProfileImage() != null) {
                    profileImage = user.getProfileImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                    ivProfileImage.setImageBitmap(bitmap);
                }
            }
        }
    }
    private void saveUserDetails() {
        String fullName = etFullName.getText().toString().trim();
        String telephone = etTelephone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(getActivity(), "Password must have at least 6 characters and one uppercase letter", Toast.LENGTH_SHORT).show();
        }
        if (!telephone.matches("\\d{10}")) {
            Toast.makeText(getActivity(), "Contact number must be exactly 10 digits", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean isUpdated = databaseHelper.updateUserDetails(currentEmail, fullName, telephone, password, profileImage);

            if (isUpdated) {
                redirectToProfilePage();
                Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update profile.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void redirectToProfilePage() {
        ProfileFragment profileFragment = new ProfileFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}