package com.example.linemaster.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linemaster.Activities.Callbacks.CallBackFragmentCalendar;
import com.example.linemaster.Activities.Fragments.Calender.AdapterCalender;
import com.example.linemaster.Activities.Fragments.NewMerchant.CurrentFragmentNewMerchant;
import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.R;

import java.util.ArrayList;

public class FragmentCalendar extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentCalendar callBackFragmentCalendar;
    private ArrayList<Appointment> appointments;
    private RecyclerView recyclerview_list_appointments;
    private AdapterCalender adapterCalender;
    private boolean theViewer;
    public static final boolean MERCHANT = true;
    public static final boolean USER = false;

    public FragmentCalendar() {
    }


    public FragmentCalendar setTheViewer(boolean bool) {
        this.theViewer = theViewer;
        return this;
    }

    public FragmentCalendar setCallBackFragmentCalendar(CallBackFragmentCalendar callBackFragmentCalendar) {
        this.callBackFragmentCalendar = callBackFragmentCalendar;
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_calender, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        recyclerview_list_appointments = view.findViewById(R.id.recyclerview_list_appointments);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterCalender= new AdapterCalender();
        recyclerview_list_appointments.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_list_appointments.setHasFixedSize(true);

        adapterCalender = new AdapterCalender();
        if(appointments != null)
            adapterCalender.setAppointment(appointments);
        else
            adapterCalender.setAppointment(new ArrayList<>());
        recyclerview_list_appointments.setAdapter(adapterCalender);
        adapterCalender.notifyDataSetChanged();
        initViews();

    }

    private void initViews() {
        adapterCalender.setOnAppointmebtClickListener(new AdapterCalender.OnAppointmentClickListener() {
            @Override
            public void removeAppointment(View v, Appointment item, int adapterPosition) {
                MyRTFB.getSpecificMerchant(item.getMerchantName(), item.getMerchantOwner(), new MyRTFB.CB_Merchant() {
                    @Override
                    public void getMerchantData(Merchant merchant) {
                        merchant.getJournal().removeAppointment(item);
                        MyRTFB.updateMerchant(merchant);
                        if(theViewer == MERCHANT){
                            setAppointments(merchant.getJournal());
                            adapterCalender.setAppointment(appointments);
                            adapterCalender.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {

                    }
                });
                MyRTFB.getUser(item.getCustomerEmail(), new MyRTFB.CB_User() {
                    @Override
                    public void getUserData(User user) {
                        if(user.getPersonalJournal()!= null){
                            user.getPersonalJournal().removeAppointment(item);
                            MyRTFB.updateUser(user);
                            if(theViewer == USER){
                                setAppointments(user.getPersonalJournal());
                                adapterCalender.setAppointment(appointments);
                                adapterCalender.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean getAllowToContinue() {
        return false;
    }

    @Override
    public void clearAll() {

    }

    public void setAppointments(Journal personalJournal) {
        appointments = personalJournal.getAppointments();
    }
}
