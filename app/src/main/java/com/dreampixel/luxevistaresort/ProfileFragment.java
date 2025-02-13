package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;


public class ProfileFragment extends Fragment {

    private TextView tvUserName, tvUserEmail;
    private ImageView ivProfileImage;
    private MaterialCardView btn_edit_profile,btn_view_booking_history;
    private MaterialButton btn_logout;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserEmail = view.findViewById(R.id.tv_user_email);
        ivProfileImage = view.findViewById(R.id.iv_profile_picture);
        btn_edit_profile=view.findViewById(R.id.btn_edit_profile);
        btn_view_booking_history=view.findViewById(R.id.btn_booking_history);
        btn_logout=view.findViewById(R.id.btn_logout);

        databaseHelper = new DatabaseHelper(getContext());

        loadUserData();

        btn_edit_profile.setOnClickListener(v->{
            ViewEditProfile();
        });
        btn_view_booking_history.setOnClickListener(v->{
            ViewBookingHistory();
        });
        btn_logout.setOnClickListener(v->{
            Logout();
        });
        return view;
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", "");

        if (!email.isEmpty()) {
            User user = databaseHelper.getUserByEmail(email);
            if (user != null) {
                tvUserName.setText(user.getFullName());
                tvUserEmail.setText(user.getEmail());

                if (user.getProfileImage() != null) {
                    Bitmap profileBitmap = BitmapFactory.decodeByteArray(user.getProfileImage(), 0, user.getProfileImage().length);
                    ivProfileImage.setImageBitmap(profileBitmap);

                } else {
                    ivProfileImage.setImageResource(R.drawable.ic_profile_placeholder);
                }
            }
        }
    }

    public void ViewEditProfile() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EditProfileFragment())
                .addToBackStack(null)
                .commit();
    }
    public void ViewBookingHistory() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BookingHistoryFragment())
                .addToBackStack(null)
                .commit();
    }

    public void Logout(){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }

}