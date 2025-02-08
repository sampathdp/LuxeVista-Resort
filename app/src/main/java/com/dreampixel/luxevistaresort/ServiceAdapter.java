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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ServiceAdapter  extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;
    private Context context;
    private DatabaseHelper dbHelper;
    private int userId; // Get the logged-in user ID

    public ServiceAdapter(List<Service> serviceList, Context context, int userId) {
        this.serviceList = serviceList;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
        this.userId = userId;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);

        holder.serviceName.setText(service.getName());
        holder.serviceDescription.setText(service.getDescription());
        holder.servicePrice.setText(String.format("$%.2f", service.getPrice()));

        if (service.getAvailability() == 1) {
            holder.serviceAvailability.setText("Available");
        } else {
            holder.serviceAvailability.setText("Unavailable");
        }

        if (service.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(service.getImage(), 0, service.getImage().length);
            holder.serviceImage.setImageBitmap(bitmap);
        } else {
            holder.serviceImage.setImageResource(R.drawable.slider_image_1);
        }

        holder.btnReserve.setOnClickListener(v -> {

            int[] validHours = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
            int randomHour = validHours[new Random().nextInt(validHours.length)];

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, randomHour);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            String reservationDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.getTime());

            boolean isReserved = dbHelper.reserveService(service.getId(), userId, reservationDateTime);
            if (isReserved) {
                Toast.makeText(context, "Service Reserved at " + randomHour + ":00!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to Reserve Service", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, serviceDescription, servicePrice, serviceAvailability;
        ImageView serviceImage;
        Button btnReserve;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceTitle);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            servicePrice = itemView.findViewById(R.id.servicePrice);
            serviceAvailability = itemView.findViewById(R.id.serviceAvailability);
            serviceImage = itemView.findViewById(R.id.serviceImage);
            btnReserve = itemView.findViewById(R.id.btnReserveNow);
        }
    }
}