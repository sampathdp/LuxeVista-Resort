package com.dreampixel.luxevistaresort;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter  extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;
    private Context context;

    public ServiceAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each service item
        View view = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        // Get the current service item
        Service service = serviceList.get(position);

        // Bind data to the views in the item layout
        holder.serviceName.setText(service.getName());
        holder.serviceDescription.setText(service.getDescription());
        holder.servicePrice.setText(String.format("$%.2f", service.getPrice()));

        // Check availability and set corresponding text
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
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, serviceDescription, servicePrice, serviceAvailability;
        ImageView serviceImage;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceTitle);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            servicePrice = itemView.findViewById(R.id.servicePrice);
            serviceAvailability = itemView.findViewById(R.id.serviceAvailability);
            serviceImage = itemView.findViewById(R.id.serviceImage);
        }
    }
}