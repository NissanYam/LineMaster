package com.example.linemaster.Activities.Fragments.NewAppointment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.Service;
import com.example.linemaster.Data.TimeRange;
import com.example.linemaster.Data.User;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.material.button.MaterialButton;
import java.time.LocalDate;
import java.util.ArrayList;

public class FragmentNewAppointment extends Fragment {
    private TextView merchant_new_appointment_name;
    private Merchant merchant;
    private Service service;
    private User user;
    private Appointment appointment;
    private CallBackFragmentNewAppointment callBackFragmentNewAppointment;
    private CalendarView merchant_new_appointment_calendarView;
    private RecyclerView merchant_new_appointment_rangeTimes;
    private MaterialButton merchant_new_appointment_order_confirmation;
    private AdapterRangeTime adapterRangeTime;
    public FragmentNewAppointment() {
    }

    public FragmentNewAppointment setCallBackFragmentNewAppointment(CallBackFragmentNewAppointment callBackFragmentNewAppointment) {
        this.callBackFragmentNewAppointment = callBackFragmentNewAppointment;
        return this;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public FragmentNewAppointment setMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public Service getService() {
        return service;
    }

    public FragmentNewAppointment setService(Service service) {
        this.service = service;
        return this;
    }

    public User getUser() {
        return user;
    }

    public FragmentNewAppointment setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_new_appointment, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        merchant_new_appointment_name = view.findViewById(R.id.merchant_new_appointment_name);
        merchant_new_appointment_calendarView = view.findViewById(R.id.merchant_new_appointment_calendarView);
        merchant_new_appointment_rangeTimes = view.findViewById(R.id.merchant_new_appointment_rangeTimes);
        merchant_new_appointment_order_confirmation = view.findViewById(R.id.merchant_new_appointment_order_confirmation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appointment = new Appointment();
        merchant_new_appointment_rangeTimes.setLayoutManager(new LinearLayoutManager(getContext()));
        merchant_new_appointment_rangeTimes.setHasFixedSize(true);
        adapterRangeTime = new AdapterRangeTime();
        adapterRangeTime.setTimeRanges(new ArrayList<>());
        merchant_new_appointment_rangeTimes.setAdapter(adapterRangeTime);
        adapterRangeTime.notifyDataSetChanged();
        initViews();
    }

    private void initViews() {
        merchant_new_appointment_name.setText(merchant.getMerchantName() + "\n" + service.getServiceName());
        merchant_new_appointment_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                if (LocalDate.now().isAfter(LocalDate.of(year, month, dayOfMonth))) {
                    adapterRangeTime.setTimeRanges(new ArrayList<>());
                    adapterRangeTime.notifyDataSetChanged();
                    return;
                }
                appointment = new Appointment().
                        setDay(dayOfMonth).
                        setMonth(month).
                        setYear(year);

                merchant_new_appointment_order_confirmation.setVisibility(View.GONE);
                adapterRangeTime.clear();
                ArrayList<TimeRange> timeRangesByDay = merchant.getAllFreeTimesByDayAndService(year, month, dayOfMonth, service);
                adapterRangeTime.setTimeRanges(timeRangesByDay);
                adapterRangeTime.notifyDataSetChanged();
            }
        });
        adapterRangeTime.setOnTimeRangeClickListener(new AdapterRangeTime.OnTimeRangeClickListener() {
            @Override
            public void timeRangeClick(View v, TimeRange item, int adapterPosition) {

                appointment.
                        setAppointmentTimeHour(item.getStartHour()).
                        setAppointmentTimeMinute(item.getStartMinutes());

                merchant_new_appointment_order_confirmation.setVisibility(View.VISIBLE);
            }
        });
        merchant_new_appointment_order_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getEmail().equals(merchant.getOwner())){
                    MySignal.getInstance().vibrate(100);
                    MySignal.getInstance().toast("The owner cannot book a place at his place");
                    return;
                }
                appointment.setMerchantName(merchant.getMerchantName()).
                        setMerchantOwner(merchant.getOwner()).
                        setCustomerEmail(user.getEmail()).
                        setService(service);
                callBackFragmentNewAppointment.addAppointment(appointment);
            }
        });
    }
}
