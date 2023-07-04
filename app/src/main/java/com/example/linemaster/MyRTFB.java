package com.example.linemaster;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;

public class MyRTFB {
    private static final String USERS ="USERS";
    private static final String MERCHANTS ="MERCHANTS";
    private static final String UNDERSCORE ="_";




    public interface CB_Merchant {
        void getMerchantData(Merchant merchant);

        void getAllMerchants(ArrayList<Merchant> merchantArrayList);
    }
    public interface CB_User {
        void getUserData(User user);
    }
    public interface CB_Img {
        void getOk();
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
        ref.child(user.getEmail().replace(POINT,SEMICOLON)).setValue(user);
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
    public static void removeMerchant(String merchantName, String userEmail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MERCHANTS);
        String key = merchantName.concat(UNDERSCORE).concat(userEmail.replace(POINT,SEMICOLON));
        ref.child(key).removeValue();
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
    public static void uploadImg(String pathInDB, Uri file,CB_Img cb_img){
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        // Create a reference to "mountains.jpg"
        StorageReference fileRef = storageRef.child("images/"+pathInDB);
        // Register observers to listen for when the download is done or if it fails
        if(file != null){
            fileRef.putFile(file).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    MySignal.getInstance().toast(exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if(cb_img != null)
                        cb_img.getOk();
                }
            });
        }

    }
    public static StorageReference getImg(String pathInDB){
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/"+pathInDB);
        return storageRef;
    }
    public static void removeAlldir(String filePath) {
        //remove data
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/"+filePath);
        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
}
