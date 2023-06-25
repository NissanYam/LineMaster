package com.example.linemaster.Activities.Fragments.AllMerchantsUser;

import static com.example.linemaster.Activities.Fragments.FragmentCalendar.MERCHANT;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linemaster.Activities.Callbacks.CallBackFragmentCalendar;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentMerchantOwnerPage;
import com.example.linemaster.Activities.Fragments.FragmentCalendar;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentMap;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantServices;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantTimes;
import com.example.linemaster.Activities.Fragments.NewMerchant.CurrentFragmentNewMerchant;
import com.example.linemaster.Activities.Fragments.NewMerchant.FragmentMap;
import com.example.linemaster.Activities.Fragments.NewMerchant.FragmentNewMerchantServices;
import com.example.linemaster.Activities.Fragments.NewMerchant.FragmentNewMerchantTimes;
import com.example.linemaster.Data.Address;
import com.example.linemaster.Data.BusinessDay;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMerchantOwnerPage extends Fragment {

    private TextView merchant_page_name;
    private CircleImageView merchant_page_style_logoIMG;
    private TextView merchant_page_phone;
    private TextView merchant_page_description;
    private MaterialButton merchant_page_BTN_address;
    private MaterialButton merchant_page_BTN_hours;
    private MaterialButton merchant_page_BTN_services;
    private MaterialButton merchant_page_BTN_calender;
    private LinearLayoutCompat merchant_page_details;
    private LinearLayoutCompat merchant_page;
    private MaterialButton merchant_page_owner_BTN_cancel;
    private MaterialButton merchant_page_owner_BTN_save;
    private Merchant merchant;
    private FragmentNewMerchantTimes fragmentNewMerchantTimes;
    private CallBackFragmentMerchantOwnerPage callBackFragmentMerchantOwnerPage;
    private FragmentMap fragmentMap;
    private CurrentFragmentNewMerchant currentFragmentMerchantPage;
    private FragmentNewMerchantServices fragmentNewMerchantServices;
    private FragmentCalendar fragmentCalendar;

    public FragmentMerchantOwnerPage() {
    }

    public FragmentMerchantOwnerPage setCallBackFragmentMerchantOwnerPage(CallBackFragmentMerchantOwnerPage callBackFragmentMerchantOwnerPage) {
        this.callBackFragmentMerchantOwnerPage = callBackFragmentMerchantOwnerPage;
        return this;
    }

    public FragmentMerchantOwnerPage setMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentNewMerchantTimes = new FragmentNewMerchantTimes();
        fragmentNewMerchantTimes.setCallBackFragmentNewMerchantTimes(this.callBackFragmentNewMerchantTimes);
        fragmentMap = new FragmentMap();
        fragmentMap.setCallBackFragmentMap(this.callBackFragmentMap);
        fragmentNewMerchantServices = new FragmentNewMerchantServices();
        fragmentNewMerchantServices.setCallBackFragmentNewMerchantServices(this.callBackFragmentNewMerchantServices);
        fragmentCalendar = new FragmentCalendar();
        fragmentCalendar.setCallBackFragmentCalendar(this.callBackFragmentCalendar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_owner_page, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        merchant_page_name = view.findViewById(R.id.merchant_page_name);
        merchant_page_style_logoIMG = view.findViewById(R.id.merchant_page_style_logoIMG);
        merchant_page_phone = view.findViewById(R.id.merchant_page_phone);
        merchant_page_description = view.findViewById(R.id.merchant_page_description);
        merchant_page_BTN_address = view.findViewById(R.id.merchant_page_BTN_address);
        merchant_page_BTN_hours = view.findViewById(R.id.merchant_page_BTN_hours);
        merchant_page_BTN_services = view.findViewById(R.id.merchant_page_BTN_services);
        merchant_page_BTN_calender = view.findViewById(R.id.merchant_page_BTN_calender);
        merchant_page_details = view.findViewById(R.id.merchant_page_details);
        merchant_page = view.findViewById(R.id.merchant_page);

        merchant_page = view.findViewById(R.id.merchant_page);
        merchant_page_owner_BTN_cancel = view.findViewById(R.id.merchant_page_owner_BTN_cancel);
        merchant_page_owner_BTN_save = view.findViewById(R.id.merchant_page_owner_BTN_save);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        merchant_page_details.setVisibility(View.VISIBLE);
        initViews();

    }

    private void initViews() {
        merchant_page_name.setText(this.merchant.getMerchantName());
        if (this.merchant.getLogo() == null) {
            merchant_page_style_logoIMG.setImageResource(R.drawable.noun_merchant_5111948);

        } else {
            merchant_page_style_logoIMG.setImageBitmap(MySignal.getInstance().StringToBitMap(this.merchant.getLogo()));
        }
        merchant_page_phone.setText(merchant.getMerchantPhone());
        merchant_page_description.setText(merchant.getDescription());
        merchant_page_BTN_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant_page_details.setVisibility(View.GONE);
                merchant_page.setVisibility(View.VISIBLE);
                replaceFragments(fragmentMap);
                currentFragmentMerchantPage = fragmentMap;
            }
        });
        merchant_page_BTN_hours.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                merchant_page_details.setVisibility(View.GONE);
                merchant_page.setVisibility(View.VISIBLE);
                fragmentNewMerchantTimes.setMerchant(merchant);
                replaceFragments(fragmentNewMerchantTimes);
                currentFragmentMerchantPage = fragmentNewMerchantTimes;
            }
        });
        merchant_page_BTN_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant_page_details.setVisibility(View.GONE);
                merchant_page.setVisibility(View.VISIBLE);
                fragmentNewMerchantServices
                        .setServices(merchant.getServices());
                replaceFragments(fragmentNewMerchantServices);
                currentFragmentMerchantPage = fragmentNewMerchantServices;
            }
        });
        merchant_page_BTN_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant_page_details.setVisibility(View.GONE);
                merchant_page.setVisibility(View.VISIBLE);
                fragmentCalendar.setAppointments(merchant.getJournal());
                fragmentCalendar.setTheViewer(MERCHANT);
                replaceFragments(fragmentCalendar);
                currentFragmentMerchantPage = fragmentCalendar;
            }
        });
        merchant_page_owner_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant_page.setVisibility(View.GONE);
                merchant_page_details.setVisibility(View.VISIBLE);

            }
        });
        merchant_page_owner_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragmentMerchantPage.equals(fragmentMap)){
                    if(lat != 0.0 && lng != 0.0) {
                        merchant.setAddress(new Address().setLng(lng).setLat(lat));
                    }
                    lng = 0.0;
                    lat = 0.0;
                }else if (currentFragmentMerchantPage.equals(fragmentNewMerchantTimes)){
                    ArrayList<BusinessDay> businessDays = (ArrayList<BusinessDay>) fragmentNewMerchantTimes.
                            getDayOfWeekBusinessDayHashMap().
                            values().
                            stream().
                            collect(Collectors.toList());
                } else if (currentFragmentMerchantPage.equals(fragmentNewMerchantServices)) {
                    merchant.setServices(fragmentNewMerchantServices.getServices());
                } else if (currentFragmentMerchantPage.equals(fragmentCalendar)) {

                }
                MyRTFB.updateMerchant(merchant);
                currentFragmentMerchantPage = null;
                merchant_page.setVisibility(View.GONE);
                merchant_page_details.setVisibility(View.VISIBLE);
            }
        });
    }
    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_merchant_page, fragment);
        fragmentTransaction.commit();
    }

    private CallBackFragmentNewMerchantTimes callBackFragmentNewMerchantTimes = new CallBackFragmentNewMerchantTimes() {
    };

    private double lat = 0.0,lng = 0.0;
    private CallBackFragmentMap callBackFragmentMap = new CallBackFragmentMap() {
        @Override
        public void sendLatLng(LatLng latLng) {
            lat = latLng.latitude;
            lng = latLng.longitude;
            MySignal.getInstance().toast("lat = "+lat+" lng = "+lng);
        }
    };
    private CallBackFragmentNewMerchantServices callBackFragmentNewMerchantServices = new CallBackFragmentNewMerchantServices() {
    };
    private CallBackFragmentCalendar callBackFragmentCalendar = new CallBackFragmentCalendar() {
    };

}
