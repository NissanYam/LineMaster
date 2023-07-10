package com.example.linemaster.Data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Journal {
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public Journal() {
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public Journal setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Appointment> getAppointmentsByDay(int year, int month, int dayOfMonth) {
        ArrayList<Appointment> appointmentsOfDay = new ArrayList<>();
        if (getAppointments() == null)
            return appointmentsOfDay;
        for (Appointment appointment : getAppointments()) {
            if (LocalDate.of(year, month, dayOfMonth) /// date we get
                    .isEqual
                            (LocalDate.of(appointment.getYear(), appointment.getMonth(), appointment.getDay()))) { /// date of appointment in loop
                appointmentsOfDay.add(appointment);
            }
        }
        return appointmentsOfDay;
    }

    public void removeAppointment(Appointment appointmentToRemove) {
        Iterator<Appointment> iterator = getAppointments().iterator();
        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.equals(appointmentToRemove)) {
                iterator.remove();
                return;
            }
        }
    }
    public Journal clone(){
        Journal journal = new Journal();
        journal.appointments = new ArrayList<>();
        for (Appointment appointment : this.appointments) {
            journal.appointments.add(appointment.clone());
        }
        return journal;
    }
}
