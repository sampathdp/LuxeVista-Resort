package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {
    private List<BookingHistoryItem> bookingHistoryList;

    public BookingHistoryAdapter(List<BookingHistoryItem> bookingHistoryList) {
        this.bookingHistoryList = bookingHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingHistoryItem item = bookingHistoryList.get(position);

        holder.bookingType.setText(item.getType());
        holder.bookingDate.setText("Booking Date: " + item.getCurrentDate());
        holder.roomOrServiceName.setText(item.getName());

        // Display check-in and check-out dates only for room bookings
        if (item.getType().equals("Room")) {
            holder.checkinDate.setText("Check-in: " + item.getCheckinDate());
            holder.checkoutDate.setText("Check-out: " + item.getCheckoutDate());

        } else {

            holder.checkinDate.setText("Service Reservation: " + item.getCheckinDate());
            holder.checkoutDate.setVisibility(View.GONE);  // Hide checkout for services
        }

        holder.totalPrice.setText("Total Price: $" + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return bookingHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingType, bookingDate, roomOrServiceName, checkinDate, checkoutDate, totalPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            bookingType = itemView.findViewById(R.id.bookingType);
            bookingDate = itemView.findViewById(R.id.currentDate);
            roomOrServiceName = itemView.findViewById(R.id.roomOrServiceName);
            checkinDate = itemView.findViewById(R.id.checkinDate);
            checkoutDate = itemView.findViewById(R.id.checkoutDate);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }
    }
}
