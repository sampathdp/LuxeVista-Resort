package com.dreampixel.luxevistaresort;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


public class BookNowFragment extends Fragment {
    private TextView checkInDate, checkOutDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_now, container, false);

        checkInDate = view.findViewById(R.id.checkInDate);
        checkOutDate = view.findViewById(R.id.checkOutDate);

        checkInDate.setOnClickListener(v -> showDatePickerDialog(checkInDate));
        checkOutDate.setOnClickListener(v -> showDatePickerDialog(checkOutDate));

        return view;
    }

    private void showDatePickerDialog(TextView targetTextView) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date
                    String formattedDate = formatDate(selectedYear, selectedMonth, selectedDay);
                    targetTextView.setText(formattedDate);
                },
                year, month, day);

        // Show the dialog
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int day) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd yyyy");
        return sdf.format(selectedDate.getTime());
    }
}