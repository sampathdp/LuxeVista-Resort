package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = new ArrayList<>(roomList);;
    }

    public void updateRoomList(List<Room> newRoomList) {
        this.roomList.clear();
        this.roomList.addAll(newRoomList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomType.setText(room.getType());
        holder.tvRoomDescription.setText(room.getDescription());
        holder.tvRoomPrice.setText("Price: $" + room.getPrice());

        // Convert BLOB to Bitmap
        byte[] imageBytes = room.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.ivRoomImage.setImageBitmap(bitmap);
        }

        holder.btnRoomBook.setOnClickListener(v ->
                Toast.makeText(context, "Booking " + room.getType(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomType, tvRoomDescription, tvRoomPrice;
        ImageView ivRoomImage;
        Button btnRoomBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tv_room_type);
            tvRoomDescription = itemView.findViewById(R.id.tv_room_description);
            tvRoomPrice = itemView.findViewById(R.id.tv_room_price);
            ivRoomImage = itemView.findViewById(R.id.iv_room_image);
            btnRoomBook = itemView.findViewById(R.id.btn_room_book);
        }
    }
}