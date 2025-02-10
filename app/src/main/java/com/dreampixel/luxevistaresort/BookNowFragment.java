package com.dreampixel.luxevistaresort;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class BookNowFragment extends Fragment {
    private TextView roomTypeText, roomPriceText, checkInDate, checkOutDate, totalPriceText;
    private ImageView roomImageView;
    private Button bookNowButton;
    private DatabaseHelper databaseHelper;
    private double roomPricePerNight;
    private long checkInMillis, checkOutMillis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_now, container, false);

        roomTypeText = view.findViewById(R.id.roomTypeText);
        roomPriceText = view.findViewById(R.id.pricePerNight);
        checkInDate = view.findViewById(R.id.checkInDate);
        checkOutDate = view.findViewById(R.id.checkOutDate);
        totalPriceText = view.findViewById(R.id.totalPriceText);
        roomImageView = view.findViewById(R.id.headerImage);
        bookNowButton = view.findViewById(R.id.bookNowButton);

        databaseHelper = new DatabaseHelper(getContext());

        getRoomDetails();

        setupDatePickers();

        bookNowButton.setOnClickListener(v -> {
            PlaceBooking();
        });

        return view;
    }

    private void getRoomDetails() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int room_ID = sharedPreferences.getInt("room_ID", -1);

        if (room_ID == -1) {
            Toast.makeText(getContext(), "Room not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Room room = databaseHelper.getRoomDetails(room_ID);

        if (room != null) {
            roomTypeText.setText(room.getType());
            roomPricePerNight = room.getPrice();
            roomPriceText.setText("Price per Night: LKR " + roomPricePerNight);

            if (room.getImage() != null) {
                Bitmap roomImage = BitmapFactory.decodeByteArray(room.getImage(), 0, room.getImage().length);
                roomImageView.setImageBitmap(roomImage);
            }
        }
    }

    private void setupDatePickers() {
        checkInDate.setOnClickListener(v -> {
            showDatePickerDialog((view, year, month, dayOfMonth) -> {
                checkInDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                checkInMillis = new GregorianCalendar(year, month, dayOfMonth).getTimeInMillis();
                calculateTotalPrice();
            });
        });
        checkOutDate.setOnClickListener(v -> {
            showDatePickerDialog((view, year, month, dayOfMonth) -> {
                checkOutDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                checkOutMillis = new GregorianCalendar(year, month, dayOfMonth).getTimeInMillis();
                calculateTotalPrice();
            });
        });
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void calculateTotalPrice() {
        if (checkInMillis != 0 && checkOutMillis != 0) {
            long diffInMillis = checkOutMillis - checkInMillis;
            long numberOfNights = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            if (numberOfNights > 0) {
                double totalPrice = roomPricePerNight * numberOfNights;
                totalPriceText.setText("Total Price: LKR " + totalPrice);
            }
        }
    }

    private  void PlaceBooking(){
        if (checkInMillis == 0 || checkOutMillis == 0 || checkOutMillis <= checkInMillis) {
            Toast.makeText(getContext(), "Please select valid check-in and check-out dates!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("user_ID", -1);
        int roomID = sharedPreferences.getInt("room_ID", 1);

        if (userID == -1) {
            Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        long numberOfNights = TimeUnit.MILLISECONDS.toDays(checkOutMillis - checkInMillis);
        double totalPrice = roomPricePerNight * numberOfNights;

        String checkInDateStr = android.text.format.DateFormat.format("yyyy-MM-dd", checkInMillis).toString();
        String checkOutDateStr = android.text.format.DateFormat.format("yyyy-MM-dd", checkOutMillis).toString();

        boolean success = databaseHelper.insertRoomBooking(userID, roomID, checkInDateStr, checkOutDateStr, "Confirmed", totalPrice);

        if (success) {
            Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Booking Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}