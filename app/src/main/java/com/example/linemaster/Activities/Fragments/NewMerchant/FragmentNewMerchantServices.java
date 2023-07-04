package com.example.linemaster.Activities.Fragments.NewMerchant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantServices;
import com.example.linemaster.Data.Service;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

public class FragmentNewMerchantServices extends Fragment implements CurrentFragmentNewMerchant {
    private CallBackFragmentNewMerchantServices callBackFragmentNewMerchantServices;
    private EditText new_merchant_services_EDT_new_service_name;
    private EditText new_merchant_services_EDT_new_service_price;
    private EditText new_merchant_services_EDT_new_service_hours;
    private EditText new_merchant_services_EDT_new_service_minutes;
    private MaterialButton new_merchant_services_BTN_new_service;
    private AdapterService adapterService;
    private RecyclerView recycleView;
    private ArrayList<Service> services;

    public AdapterService getAdapterService() {
        return adapterService;
    }

    public FragmentNewMerchantServices setAdapterService(AdapterService adapterService) {
        this.adapterService = adapterService;
        return this;
    }

    public FragmentNewMerchantServices setServices(ArrayList<Service> services) {
        this.services = services;
        return this;
    }

    public FragmentNewMerchantServices() {
    }

    public FragmentNewMerchantServices setCallBackFragmentNewMerchantServices(CallBackFragmentNewMerchantServices callBackFragmentNewMerchantServices) {
        this.callBackFragmentNewMerchantServices = callBackFragmentNewMerchantServices;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_merchant_services, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        new_merchant_services_EDT_new_service_name = view.findViewById(R.id.new_merchant_services_EDT_new_service_name);
        new_merchant_services_EDT_new_service_price = view.findViewById(R.id.new_merchant_services_EDT_new_service_price);
        new_merchant_services_EDT_new_service_hours = view.findViewById(R.id.new_merchant_services_EDT_new_service_hours);
        new_merchant_services_EDT_new_service_minutes = view.findViewById(R.id.new_merchant_services_EDT_new_service_minutes);
        new_merchant_services_BTN_new_service = view.findViewById(R.id.new_merchant_services_BTN_new_service);
        recycleView = view.findViewById(R.id.recyclerview_list_Services);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setHasFixedSize(true);
        if(services == null)
            services = new ArrayList<>();
        adapterService = new AdapterService(services);
        adapterService.setOnServiceClickListener((v, item, adapterPosition) -> {
            services.remove(item);
            adapterService.notifyItemRemoved(adapterPosition);
        });
        recycleView.setAdapter(adapterService);
        adapterService.notifyDataSetChanged();
    }

    private void initViews() {
        new_merchant_services_BTN_new_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_merchant_services_EDT_new_service_name.getText().toString().isEmpty() ||
                        new_merchant_services_EDT_new_service_name.getText().toString().replaceAll(" ", "").isEmpty()) {
                    MySignal.getInstance().playYoyo(new_merchant_services_EDT_new_service_name);
                    return;
                }
                if (new_merchant_services_EDT_new_service_price.getText().toString().isEmpty() ||
                        new_merchant_services_EDT_new_service_price.getText().toString().replaceAll(" ", "").isEmpty()) {
                    MySignal.getInstance().playYoyo(new_merchant_services_EDT_new_service_price);
                    return;
                }
                if (!isValidTime(new_merchant_services_EDT_new_service_hours.getText().toString(),
                        new_merchant_services_EDT_new_service_minutes.getText().toString())) {
                    MySignal.getInstance().playYoyo(new_merchant_services_EDT_new_service_hours);
                    MySignal.getInstance().playYoyo(new_merchant_services_EDT_new_service_minutes);
                    return;
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Service service = new Service()
                            .setServiceName(new_merchant_services_EDT_new_service_name.getText().toString())
                            .setPrice(Integer.parseInt(new_merchant_services_EDT_new_service_price.getText().toString()))
                            .setServiceTimeHour(Integer.parseInt(new_merchant_services_EDT_new_service_hours.getText().toString()))
                            .setServiceTimeMinutes(Integer.parseInt(new_merchant_services_EDT_new_service_minutes.getText().toString()));
                    new_merchant_services_EDT_new_service_name.setText("");
                    new_merchant_services_EDT_new_service_price.setText("");
                    new_merchant_services_EDT_new_service_minutes.setText("");
                    new_merchant_services_EDT_new_service_hours.setText("");
                    services.add(service);
                    adapterService.setServices(services).notifyDataSetChanged();
                }
            }
        });
    }

    private boolean isValidTime(String hourString, String minuteString) {
        // Check if the hour and minute strings match the valid format
        if (!hourString.matches("^([0-1]?[0-9]|2[0-3])$") || !minuteString.matches("^([0-5]?[0-9])$")) {
            return false;
        }

        // Convert the hour and minute strings to integers
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);

        // Check if the hour and minute values are within the valid range
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return false;
        }

        // The hour and minute values are valid
        return true;
    }

    @Override
    public boolean getAllowToContinue() {
        if (services.size() == 0)
            return false;
        return true;
    }

    @Override
    public void clearAll() {
        new_merchant_services_EDT_new_service_name.setText("");
        new_merchant_services_EDT_new_service_price.setText("");
        new_merchant_services_EDT_new_service_hours.setText("");
        new_merchant_services_EDT_new_service_minutes.setText("");
        services = new ArrayList<>();
        adapterService.setServices(services).notifyDataSetChanged();
    }

    public ArrayList<Service> getServices() {
        return services;
    }
}
