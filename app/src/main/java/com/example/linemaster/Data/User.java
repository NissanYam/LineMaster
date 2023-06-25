package com.example.linemaster.Data;

import java.util.ArrayList;

public class User {
    private String email;
    private String phoneNumber = "0";
    private String firstName;
    private String lastName;
    private Journal personalJournal;
    private String picture = "0";
    private ArrayList<String> merchants;

    public User() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Journal getPersonalJournal() {
        return personalJournal;
    }

    public User setPersonalJournal(Journal personalJournal) {
        this.personalJournal = personalJournal;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public User setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public ArrayList<String> getMerchants() {
        return merchants;
    }

    public User setMerchants(ArrayList<String> merchants) {
        this.merchants = merchants;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public void setNewMerchant(String merchantName) {
        try{
            this.merchants.add(merchantName);
        }catch (Exception e){
            this.merchants = new ArrayList<>();
            this.merchants.add(merchantName);
        }
    }

}
