package com.example.linemaster.Data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.linemaster.MySignal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Merchant {
    private String merchantName;
    private String merchantPhone;
    private ArrayList<MerchantType> merchantType = new ArrayList<>();
    private String owner;
    private Address address;
    private String logo;
    private Journal journal = new Journal();
    private String description;
    private ArrayList<BusinessDay> businessDays = new ArrayList<>();
    private ArrayList<Service> services = new ArrayList<>();


    public Merchant() {
    }

    public String getMerchantName() {
        return merchantName;
    }

    public Merchant setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public ArrayList<MerchantType> getMerchantType() {
        return merchantType;
    }

    public Merchant setMerchantType(ArrayList<MerchantType> merchantType) {
        this.merchantType = merchantType;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public Merchant setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Merchant setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public Merchant setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Journal getJournal() {
        return journal;
    }

    public Merchant setJournal(Journal journal) {
        this.journal = journal;
        return this;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public Merchant setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Merchant setDescription(String description) {
        this.description = description;
        return this;
    }

    public ArrayList<BusinessDay> getBusinessDays() {
        return businessDays;
    }

    public Merchant setBusinessDays(ArrayList<BusinessDay> businessDays) {
        this.businessDays = businessDays;
        return this;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public Merchant setServices(ArrayList<Service> services) {
        this.services = services;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<TimeRange> getAllFreeTimesByDayAndService(int year, int month, int dayOfMonth,Service service) {
        BusinessDay businessDay = getDayOfWeek(
                LocalDate.of(year, month, dayOfMonth)// date we get
                        .getDayOfWeek());
        if (businessDay == null || !businessDay.isActive()) {
            if(businessDay != null)
                MySignal.getInstance().toast("The merchant ".concat(merchantName).concat(" not active during the day ".concat(businessDay.getDayOfWeek().toString())));
            return new ArrayList<>();
        }

        ArrayList<TimeRange> availableTimes = new ArrayList<>();

        // Calculate the start time and end time of the working range
        int startHour = businessDay.getTimeRanges().getStartHour();
        int startMinutes = businessDay.getTimeRanges().getStartMinutes();
        int endHour = businessDay.getTimeRanges().getEndHour();
        int endMinutes = businessDay.getTimeRanges().getEndMinutes();
        MySignal.getInstance().toast(businessDay.getDayOfWeek().toString()+" "+startHour+":"+startMinutes+" -- "+endHour+":"+endMinutes);
        // Round the start time to the nearest valid appointment start time (00, 15, or 30 minutes)
        int roundedStartMinutes = startMinutes;
        if (startMinutes % 15 != 0) {
            roundedStartMinutes = startMinutes + (15 - (startMinutes % 15));
        }
        if(this.journal == null){
            setJournal(new Journal().setAppointments(new ArrayList<>()));
        }
        ArrayList<Appointment> appointmentsInDay = this.journal.getAppointmentsByDay(year, month, dayOfMonth);

        // Calculate the duration of the desired service in minutes
        int serviceDurationMinutes = service.getServiceTimeHour() * 60 + service.getServiceTimeMinutes();
        if(endHour == 0){
            endHour = 24;
        }
        for (int hour = startHour; hour <= endHour; hour++) {
            for (int minutes = roundedStartMinutes; minutes <= 45; minutes += 15) {
                // Check if the appointment time is available
                int endNewAppointmentHour = hour+service.getServiceTimeHour();
                int endNewAppointmentMinute = (minutes+service.getServiceTimeMinutes())%60;
                if( endNewAppointmentHour > endHour ||
                        (endNewAppointmentHour == endHour && endNewAppointmentMinute > endMinutes)){
                    break;
                }
                // Iterate over existing appointments and check for conflicts
                if(iterAppointmentsCheckConflicts(appointmentsInDay,hour,minutes,serviceDurationMinutes)){
                    // If the appointment time is available, add it to the list
                    TimeRange availableTime = new TimeRange()
                            .setStartHour(hour)
                            .setStartMinutes(minutes)
                            .setEndHour(hour + (minutes + serviceDurationMinutes) / 60)
                            .setEndMinutes((minutes + serviceDurationMinutes) % 60);

                    availableTimes.add(availableTime);
                }
            }
            // Reset the start minutes after the first iteration
            roundedStartMinutes = 0;
        }
        if(availableTimes.size() == 0)
            MySignal.getInstance().toast("There are no more times available today for this service");
        return availableTimes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean iterAppointmentsCheckConflicts(ArrayList<Appointment> appointmentsInDay, int startNewAppointmentHour, int startNewAppointmentMinutes, int serviceDurationMinutes) {
        int endNewAppointmentHour = startNewAppointmentHour+(startNewAppointmentMinutes+serviceDurationMinutes)/60;
        int endNewAppointmentMinute = (startNewAppointmentMinutes+serviceDurationMinutes)%60;
        try {
            LocalTime startNew = LocalTime.of(startNewAppointmentHour,startNewAppointmentMinutes);
            LocalTime endNew = LocalTime.of(endNewAppointmentHour,endNewAppointmentMinute);
            for (Appointment appointment : appointmentsInDay) {
                LocalTime startAppoint = LocalTime.of(appointment.getAppointmentTimeHour(),appointment.getAppointmentTimeMinute());
                int endAppointHour = appointment.getAppointmentTimeHour()
                        +appointment.getService().getServiceTimeHour()
                        +((appointment.getAppointmentTimeMinute()+appointment.getService().getServiceTimeMinutes())/60);
                int endAppointMinute = (appointment.getAppointmentTimeMinute()+appointment.getService().getServiceTimeMinutes())%60;
                LocalTime endAppoint = LocalTime.of(endAppointHour,endAppointMinute);
                if(startNew.compareTo(startAppoint) == 0 && endNew.compareTo(endAppoint) == 0)
                    return false;
                if(endNew.isBefore(endAppoint) && endNew.isAfter(startAppoint))
                    return false;
                if(startNew.isAfter(startAppoint) && startNew.isBefore(endAppoint))
                    return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private BusinessDay getDayOfWeek(DayOfWeek dayOfWeek) {
        for (BusinessDay businessDay : this.businessDays) {
            if (businessDay.getDayOfWeek() == dayOfWeek)
                return businessDay;
        }
        return null;
    }

    public BusinessDay getBusinessDay(DayOfWeek day) {
        for (BusinessDay businessDay : this.getBusinessDays()) {
            if(businessDay.getDayOfWeek() == day)
                return businessDay;
        }
        return null;
    }
}
