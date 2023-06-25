package com.example.linemaster.Data;

import java.time.LocalTime;

public class Service {
    private String serviceName;
    private int price;
    private int serviceTimeHour;
    private int serviceTimeMinutes;
    public Service() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public Service setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Service setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getServiceTimeHour() {
        return serviceTimeHour;
    }

    public Service setServiceTimeHour(int serviceTimeHour) {
        this.serviceTimeHour = serviceTimeHour;
        return this;
    }

    public int getServiceTimeMinutes() {
        return serviceTimeMinutes;
    }

    public Service setServiceTimeMinutes(int serviceTimeMinutes) {
        this.serviceTimeMinutes = serviceTimeMinutes;
        return this;
    }
    public boolean equals(Service service){
        if(this.serviceName.equals(service.getServiceName()))
            if(this.serviceTimeHour == service.getServiceTimeHour())
                if(this.serviceTimeMinutes == service.getServiceTimeMinutes())
                    if(this.price == service.getPrice())
                        return true;
        return false;
    }
}
