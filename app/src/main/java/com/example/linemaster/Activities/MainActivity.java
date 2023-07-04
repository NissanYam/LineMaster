package com.example.linemaster.Activities;

import static com.example.linemaster.Activities.Fragments.Calender.FragmentCalendar.USER;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.akki.circlemenu.CircleMenu;
import com.akki.circlemenu.OnCircleMenuItemClicked;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentAllMerchants;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentCalendar;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentHome;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentMerchantHomePage;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentMerchantOwnerPage;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentNewMerchant;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentProfile;
import com.example.linemaster.Activities.Fragments.AllMerchantsUser.FragmentAllMerchants;
import com.example.linemaster.Activities.Fragments.AllMerchantsUser.FragmentMerchantOwnerPage;
import com.example.linemaster.Activities.Fragments.Calender.FragmentCalendar;
import com.example.linemaster.Activities.Fragments.FragmentHome;
import com.example.linemaster.Activities.Fragments.FragmentMerchantHomePage;
import com.example.linemaster.Activities.Fragments.NewAppointment.CallBackFragmentNewAppointment;
import com.example.linemaster.Activities.Fragments.NewAppointment.FragmentNewAppointment;
import com.example.linemaster.Activities.Fragments.NewMerchant.FragmentNewMerchant;
import com.example.linemaster.Activities.Fragments.FragmentProfile;
import com.example.linemaster.Data.Appointment;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.Service;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView main_IMG_return;
    private FragmentHome fragmentHome;
    private FragmentProfile fragmentProfile;
    private FragmentCalendar fragmentCalendar;
    private FragmentAllMerchants fragmentAllMerchants;
    private FragmentNewMerchant fragmentNewMerchant;
    private FragmentMerchantOwnerPage fragmentMerchantOwnerPage;
    private FragmentMerchantHomePage fragmentMerchantHomePage;
    private CircleMenu main_circleMenu;
    private ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        main_IMG_return.setVisibility(View.INVISIBLE);
        fragmentHome = new FragmentHome();
        fragmentHome.setCallBackFragmentHome(this.callBackFragmentHome);
        fragmentHome.setMerchants(new ArrayList<>());
        fragmentProfile = new FragmentProfile();
        fragmentProfile.setCallBackFragmentProfile(this.callBackFragmentProfile);
        fragmentCalendar = new FragmentCalendar();
        fragmentCalendar.setCallBackFragmentCalendar(this.callBackFragmentCalendar);
        fragmentAllMerchants = new FragmentAllMerchants();
        fragmentAllMerchants.setCallBackFragmentAllMerchants(this.callBackFragmentAllMerchants);
        fragmentNewMerchant = new FragmentNewMerchant();
        fragmentNewMerchant.setCallBackFragmentNewMerchant(this.callBackFragmentNewMerchant);
        fragmentMerchantOwnerPage = new FragmentMerchantOwnerPage();
        fragmentMerchantOwnerPage.setCallBackFragmentMerchantOwnerPage(this.callBackFragmentMerchantOwnerPage);
        fragmentMerchantHomePage = new FragmentMerchantHomePage();
        fragmentMerchantHomePage.setCallBackFragmentMerchantHomePage(this.callBackFragmentMerchantHomePage);
        homePage();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initViews() {
        main_IMG_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePage();
            }
        });
        main_circleMenu.setOnMenuItemClickListener(new OnCircleMenuItemClicked() {
            @Override
            public void onMenuItemClicked(int i) {
                if(i == R.drawable.noun_search_5784563){ /// home
                    homePage();
                    return;
                } else if (i == R.drawable.noun_new_5563868) { /// new merchant
                    replaceFragments(fragmentNewMerchant);
                } else if (i == R.drawable.noun_merchant_5111948) { /// all user merchants
                    //get all merchant from DB and send team to fragment
                    allUserMerchants();
                }else if (i == R.drawable.calendar_svgrepo_com) { //my calender
                    myCalender();
                }else if (i == R.drawable.profile_round_1342_svgrepo_com) {// my profile
                    profile();
                }
                main_IMG_return.setVisibility(View.VISIBLE);
                main_circleMenu.setVisibility(View.INVISIBLE);
            }
        });
        logout.setOnClickListener(v -> logOut());
    }
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void profile() {
        MyRTFB.getUser(MySignal.getInstance().getUserEmail(), new MyRTFB.CB_User() {
            @Override
            public void getUserData(User user) {
                if(user == null) {
                    MySignal.getInstance().toast("Error Try log in with uppercase/lowercase in first letter");
                    homePage();
                    return;
                }
                fragmentProfile.setUser(user);
                replaceFragments(fragmentProfile);
            }
        });
    }
    private void myCalender() {
        //get all user appointments
        MyRTFB.getUser(MySignal.getInstance().getUserEmail(), new MyRTFB.CB_User() {
            @Override
            public void getUserData(User user) {
                if(user == null){
                    return;
                }
                if(user.getPersonalJournal() != null) {
                    fragmentCalendar.setAppointments(user.getPersonalJournal());
                }else {
                    fragmentCalendar.setAppointments(new Journal().setAppointments(new ArrayList<>()));
                }
                fragmentCalendar.setTheViewer(USER);
                replaceFragments(fragmentCalendar);
            }
        });
    }
    private void allUserMerchants() {
        MyRTFB.getAllMerchantsByUser(MySignal.getInstance().getUserEmail(), new MyRTFB.CB_Merchant() {
            @Override
            public void getMerchantData(Merchant merchant) {
                ///Unused
            }

            @Override
            public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {
                if(merchantArrayList == null){
                    merchantArrayList = new ArrayList<>();
                }
                fragmentAllMerchants.setMerchants(merchantArrayList);
                replaceFragments(fragmentAllMerchants);
            }
        });
    }
    private void homePage() {
        MyRTFB.getAllMerchants(new MyRTFB.CB_Merchant() {
            @Override
            public void getMerchantData(Merchant merchant) {
                ///Unused
            }

            @Override
            public void getAllMerchants(ArrayList<Merchant> merchantArrayList) {
                fragmentHome.setMerchants(merchantArrayList);
                replaceFragments(fragmentHome);
                main_IMG_return.setVisibility(View.INVISIBLE);
                main_circleMenu.setVisibility(View.VISIBLE);
            }
        });
    }
    private void findViews() {
        main_IMG_return = findViewById(R.id.main_IMG_return);
        main_circleMenu = findViewById(R.id.main_circle_menu);
        logout = findViewById(R.id.logout);

    }
    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_LAY, fragment);
        fragmentTransaction.commit();
    }
    private CallBackFragmentHome callBackFragmentHome = new CallBackFragmentHome() {
        @Override
        public void openMerchantPage(Merchant item) {
            if(item != null){
                fragmentMerchantHomePage.setMerchant(item);
                replaceFragments(fragmentMerchantHomePage);
                main_IMG_return.setVisibility(View.VISIBLE);
                main_circleMenu.setVisibility(View.INVISIBLE);
            }
        }
    };
    private CallBackFragmentProfile callBackFragmentProfile = new CallBackFragmentProfile() {
    };
    private CallBackFragmentCalendar callBackFragmentCalendar = new CallBackFragmentCalendar() {
    };
    private CallBackFragmentAllMerchants callBackFragmentAllMerchants = new CallBackFragmentAllMerchants() {
        @Override
        public void openMerchantPage(Merchant item) {
            fragmentMerchantOwnerPage.setMerchant(item);
            replaceFragments(fragmentMerchantOwnerPage);
        }
    };
    private CallBackFragmentNewMerchant callBackFragmentNewMerchant = new CallBackFragmentNewMerchant() {
        @Override
        public void theNewMerchantDone(Merchant merchant) {
            //add merchant to DB
            if(!merchant.getLogo().equals("")) {
                Uri fileUri = Uri.parse(merchant.getLogo());
                merchant.setLogo(String.valueOf(Uri.parse(
                        merchant.getOwner()
                                .concat("_")
                                .concat(merchant.getMerchantName()))));
                MyRTFB.uploadImg(merchant.getLogo(), fileUri,null);
            }
            MyRTFB.saveNewMerchant(merchant);
            main_IMG_return.setVisibility(View.INVISIBLE);
            main_circleMenu.setVisibility(View.VISIBLE);
            replaceFragments(fragmentHome);
        }
    };
    private CallBackFragmentMerchantOwnerPage callBackFragmentMerchantOwnerPage = new CallBackFragmentMerchantOwnerPage() {
        @Override
        public void returnToHome() {
            homePage();
        }
    };
    private CallBackFragmentMerchantHomePage callBackFragmentMerchantHomePage = new CallBackFragmentMerchantHomePage() {
        @Override
        public void openFragmentNewAppointment(Merchant merchant, Service item) {
            if(merchant != null && item != null){
                MyRTFB.getUser(MySignal.getInstance().getUserEmail(), new MyRTFB.CB_User() {
                    @Override
                    public void getUserData(User user) {
                        if(user != null){
                            FragmentNewAppointment fragmentNewAppointment = new FragmentNewAppointment();
                            fragmentNewAppointment.setMerchant(merchant).setUser(user).setService(item);
                            fragmentNewAppointment.setCallBackFragmentNewAppointment(new CallBackFragmentNewAppointment() {
                                @Override
                                public void addAppointment(Appointment appointment) {
                                    MyRTFB.saveNewAppointment(appointment);
                                    homePage();
                                }
                            });
                            replaceFragments(fragmentNewAppointment);
                            main_IMG_return.setVisibility(View.VISIBLE);
                            main_circleMenu.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }
    };
}
