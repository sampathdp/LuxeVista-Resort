package com.dreampixel.luxevistaresort;

import static java.security.AccessController.getContext;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ServiceAdapter  extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;
    private Context context;
    private DatabaseHelper dbHelper;
    private int userId;

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

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_ID", -1);

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
            holder.serviceAvailability.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary));
        } else {
            holder.serviceAvailability.setText("Unavailable");
            holder.serviceAvailability.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

        if (service.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(service.getImage(), 0, service.getImage().length);
            holder.serviceImage.setImageBitmap(bitmap);
        } else {
            holder.serviceImage.setImageResource(R.drawable.slider_image_1);
        }

        holder.btnReserve.setOnClickListener(v -> {

            int[] validHours = {10, 11, 12, 13, 14, 15, 16, 17, 18};
            int randomHour = validHours[new Random().nextInt(validHours.length)];
            Calendar calendar = Calendar.getInstance();
            int randomDayOffset = new Random().nextInt(7);
            calendar.add(Calendar.DAY_OF_YEAR, randomDayOffset);

            calendar.set(Calendar.HOUR_OF_DAY, randomHour);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            String reservationDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.getTime());


            boolean isReserved = dbHelper.reserveService(service.getId(), userId, reservationDateTime);

            if (service.getAvailability() != 1) {
                Toast.makeText(context, "Service Not Available", Toast.LENGTH_SHORT).show();
            }else if(userId==-1){
                Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
            }
            else if (isReserved) {
                Toast.makeText(context, "Service Reserved for " + new SimpleDateFormat("EEEE, MMM dd", Locale.getDefault()).format(calendar.getTime()) + " at " + randomHour + ":00!", Toast.LENGTH_SHORT).show();
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