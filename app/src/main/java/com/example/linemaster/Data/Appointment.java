package com.example.linemaster.Data;

public class Appointment {
    private String customerEmail;
    private String merchantName;
    private String merchantOwner;
    private int appointmentTimeHour;
    private int appointmentTimeMinute;
    private Service service;
    private int day;
    private int month;
    private int year;

    public Appointment() {
    }

    public int getAppointmentTimeHour() {
        return appointmentTimeHour;
    }

    public Appointment setAppointmentTimeHour(int appointmentTimeHour) {
        this.appointmentTimeHour = appointmentTimeHour;
        return this;
    }

    public int getAppointmentTimeMinute() {
        return appointmentTimeMinute;
    }

    public Appointment setAppointmentTimeMinute(int appointmentTimeMinute) {
        this.appointmentTimeMinute = appointmentTimeMinute;
        return this;
    }

    public int getDay() {
        return day;
    }

    public Appointment setDay(int day) {
        this.day = day;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public Appointment setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Appointment setYear(int year) {
        this.year = year;
        return this;
    }

    public Service getService() {
        return service;
    }

    public Appointment setService(Service service) {
        this.service = service;
        return this;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Appointment setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public Appointment setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public String getMerchantOwner() {
        return merchantOwner;
    }

    public Appointment setMerchantOwner(String merchantOwner) {
        this.merchantOwner = merchantOwner;
        return this;
    }
    public boolean equals(Appointment appointment){
        if(this.customerEmail.equals(appointment.getCustomerEmail()))
            if(this.merchantName.equals(appointment.getMerchantName()))
                if(this.merchantOwner.equals(appointment.getMerchantOwner()))
                    if(this.appointmentTimeHour == appointment.getAppointmentTimeHour())
                        if(this.appointmentTimeMinute == appointment.getAppointmentTimeMinute())
                            if(this.day == appointment.getDay())
                                if(this.month == appointment.getMonth())
                                    if(this.year == appointment.getYear())
                                        if(this.service.equals(appointment.getService()))
                                            return true;
        return false;
    }
}
