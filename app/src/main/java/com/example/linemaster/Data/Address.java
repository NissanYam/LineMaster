package com.example.linemaster.Data;

public class Address {
    private double lat;
    private double lng;

    public Address() {
    }

    public double getLat() {
        return lat;
    }

    public Address setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Address setLng(double lng) {
        this.lng = lng;
        return this;
    }
    public Address clone(){
        Address address = new Address();
        address.lat = this.lat;
        address.lng = this.lng;
        return address;
    }
}
