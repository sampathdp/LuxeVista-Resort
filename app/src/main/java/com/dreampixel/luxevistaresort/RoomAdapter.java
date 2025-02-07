package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter  extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Room> rooms;
    private Context context;

    public RoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = rooms.get(position);

        holder.roomType.setText(room.getType());
        holder.roomDescription.setText(room.getDescription());
        holder.roomPrice.setText(String.format("LKR %.2f per night", room.getPrice()));

        if (room.getImage() != null) {
            holder.roomImage.setImageBitmap(BitmapFactory.decodeByteArray(room.getImage(), 0, room.getImage().length));
        }


        holder.bookButton.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("room_ID", room.getId());
            editor.apply();

            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BookNowFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView roomType, roomDescription, roomPrice;
        Button bookButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.iv_room_image);
            roomType = itemView.findViewById(R.id.tv_room_type);
            roomDescription = itemView.findViewById(R.id.tv_room_description);
            roomPrice = itemView.findViewById(R.id.tv_room_price);
            bookButton = itemView.findViewById(R.id.btn_room_book);
        }
    }
}