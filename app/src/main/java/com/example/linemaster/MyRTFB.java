package com.example.linemaster;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MyRTFB {
    private static final String USERS ="USERS";
    private static final String MERCHANTS ="MERCHANTS";
    private static final String UNDERSCORE ="_";
    private static final String JOURNAL ="Journal";





    public interface CB_Merchant {
        void getMerchantData(Merchant merchant);

        void getAllMerchants(ArrayList<Merchant> merchantArrayList);
    }

    public interface CB_User {
        void getUserData(User user);
    }

    private static final String POINT = ".";
    private static final String SEMICOLON = ";";
    public static void saveNewUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(USERS);
        ref.child(user.getEmail().replace(POINT,SEMICOLON)).setValue(user);
    }
    public static void getUser(String email, CB_User cb_user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(USERS);
        String key = email.replace(POINT,SEMICOLON);
        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    User user = snapshot.getValue(User.class);
                    cb_user.getUserData(user);
                } catch (Exception ex) {
                    throw ex;
//                    cb_user.getUserData(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cb_user.getUserData(null);
            }
        });
    }
    public static void saveNewMerchant(Merchant merchant) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MERCHANTS);
        String key = merchant.getMerchantName()+UNDERSCORE+merchant.getOwner().replace(POINT, SEMICOLON);
        ref.child(key).setValue(merchant);
        getUser(merchant.getOwner(), new CB_User() {
            @Override
            public void getUserData(User user) {
                user.setNewMerchant(merchant.getMerchantName());
                updateUser(user);
            }
        });
    }
    public static void updateUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(USERS);
        ref.child(user.getEmail().replace(POINT,SEMICOLON)).
                setValue(user);
    }
    public static void getAllMerchants(CB_Merchant cb_merchant){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MERCHANTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Merchant> merchants = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Merchant merchant = child.getValue(Merchant.class);
                    merchants.add(merchant);
                }
                cb_merchant.getAllMerchants(merchants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                MySignal.getInstance().toast("Fail");
                cb_merchant.getAllMerchants(null);
            }
        });
    }
    public static void getSpecificMerchant(String merchantName, String userEmail, CB_Merchant cb_merchant){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MERCHANTS);
        String key = merchantName.concat(UNDERSCORE).concat(userEmail.replace(POINT,SEMICOLON));
        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Merchant merchant = snapshot.getValue(Merchant.class);
                cb_merchant.getMerchantData(merchant);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                MySignal.getInstance().toast("Fail");
                cb_merchant.getMerchantData(null);
            }
        });
    }
    public static void getAllMerchantsByUser(String email ,CB_Merchant cb_merchant){
        /**
         * get specific user
        * */
        getUser(email, new CB_User() {
            @Override
            public void getUserData(User user) {
                if(user == null)
                    return;
                /**get all merchants by user
                 * */
                if(user.getMerchants() != null) {
                    ArrayList<String> merchantsNames = user.getMerchants();
                    ArrayList<Merchant> merchants = new ArrayList<>();
                    for (String merchantsName: merchantsNames) {
                        getSpecificMerchant(merchantsName, user.getEmail(), new CB_Merchant() {
                            @Override
                            public void getMerchantData(Merchant merchant) {
                                merchants.add(merchant);
                                if(merchants.size() == merchantsNames.size())
                                    cb_merchant.getAllMerchants(merchants);
                            }

                            @Override
                            public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {
                                //Unused
                            }
                        });
                    }
                }
            }
        });
    }
    public static void updateMerchant(Merchant merchant) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MERCHANTS);
        String key = merchant.getMerchantName()+UNDERSCORE+merchant.getOwner().replace(POINT,SEMICOLON);
        ref.child(key).setValue(merchant);
    }
    public static void saveNewAppointment(Appointment appointment) {
        getSpecificMerchant(appointment.getMerchantName(), appointment.getMerchantOwner(), new CB_Merchant() {
            @Override
            public void getMerchantData(Merchant merchant) {
                if(merchant.getJournal() == null){
                    merchant.setJournal(new Journal().setAppointments(new ArrayList<>()));
                }
                merchant.getJournal().getAppointments().add(appointment);
                updateMerchant(merchant);
            }

            @Override
            public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {

            }
        });
        getUser(appointment.getCustomerEmail(), new CB_User() {
            @Override
            public void getUserData(User user) {
                if(user.getPersonalJournal() == null){
                    user.setPersonalJournal(new Journal().setAppointments(new ArrayList<>()));
                }
                user.getPersonalJournal().getAppointments().add(appointment);
                updateUser(user);
            }
        });

    }


}
