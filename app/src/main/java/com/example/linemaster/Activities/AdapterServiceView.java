package com.example.linemaster.Activities;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linemaster.Data.Service;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

import java.util.List;

public class AdapterServiceView extends RecyclerView.Adapter<AdapterServiceView.ServiceViewHolder> {


    private List<Service> services;
    private OnServiceClickListener onServiceClickListener;

    public AdapterServiceView(List<Service> services) {
        this.services = services;
    }

    public void setOnServiceClickListener(OnServiceClickListener onServiceClickListener) {
        this.onServiceClickListener = onServiceClickListener;
    }

    public List<Service> getServices() {
        return services;
    }

    public AdapterServiceView setServices(List<Service> services) {
        this.services = services;
        return this;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.services_item, viewGroup, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = getItem(position);
        holder.service_item_TXT_name.setText(service.getServiceName());
        holder.service_item_TXT_price.setText(service.getPrice() + " $$");
        holder.service_item_TXT_time.setText(MySignal.getInstance().formatTime(service.getServiceTimeHour(), service.getServiceTimeMinutes()));
    }

    @Override
    public int getItemCount() {
        return services == null ? 0 : services.size();
    }

    private Service getItem(int position) {
        return services.get(position);
    }

    public interface OnServiceClickListener {
        void openNewReservation(View v, Service item, int adapterPosition);
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView service_item_IMG_trash;
        private TextView service_item_TXT_name;
        private TextView service_item_TXT_price;
        private TextView service_item_TXT_time;
        private LinearLayoutCompat linearLayoutCompat_info;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            initViews();
        }

        private void initViews() {
            service_item_IMG_trash.setVisibility(View.GONE);
            linearLayoutCompat_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onServiceClickListener.openNewReservation(v,getItem(getAdapterPosition()),getAdapterPosition());
                }
            });
        }

        private void findViews(View itemView) {
            service_item_IMG_trash = itemView.findViewById(R.id.service_item_IMG_trash);
            service_item_TXT_name = itemView.findViewById(R.id.service_item_TXT_name);
            service_item_TXT_price = itemView.findViewById(R.id.service_item_TXT_price);
            service_item_TXT_time = itemView.findViewById(R.id.service_item_TXT_time);
            linearLayoutCompat_info = itemView.findViewById(R.id.linearLayoutCompat_info);
        }
    }
}
