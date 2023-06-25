package com.example.linemaster.Activities.Fragments.Calender;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentCalendar;
import com.example.linemaster.Activities.Fragments.NewMerchant.CurrentFragmentNewMerchant;
import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Journal;
import java.util.ArrayList;

public class FragmentCalendar extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentCalendar callBackFragmentCalendar;
    private ArrayList<Appointment> appointments;
    private RecyclerView recyclerview_list_appointments;


    public FragmentCalendar() {
    }

    public FragmentCalendar setCallBackFragmentCalendar(CallBackFragmentCalendar callBackFragmentCalendar) {
        this.callBackFragmentCalendar = callBackFragmentCalendar;
        return this;
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
