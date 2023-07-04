package com.example.linemaster.Activities.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Activities.AdapterServiceView;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentMerchantHomePage;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.Service;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

import java.io.File;

public class FragmentMerchantHomePage extends Fragment {
    private Merchant merchant;
    private TextView merchant_page_merchant_name;
    private ImageView merchant_page_logo;
    private TextView merchant_home_page_description;
    private TextView merchant_home_page_location;
    private RecyclerView merchant_home_page_services;
    private TextView merchant_home_page_Phone_number;
    private CallBackFragmentMerchantHomePage callBackFragmentMerchantHomePage;
    private AdapterServiceView adapterServiceView;

    public CallBackFragmentMerchantHomePage getCallBackFragmentMerchantHomePage() {
        return callBackFragmentMerchantHomePage;
    }

    public FragmentMerchantHomePage setCallBackFragmentMerchantHomePage(CallBackFragmentMerchantHomePage callBackFragmentMerchantHomePage) {
        this.callBackFragmentMerchantHomePage = callBackFragmentMerchantHomePage;
        return this;
    }

    public FragmentMerchantHomePage() {
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public FragmentMerchantHomePage setMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_home_page, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        merchant_page_merchant_name = view.findViewById(R.id.merchant_page_merchant_name);
        merchant_page_logo = view.findViewById(R.id.merchant_page_logo);
        merchant_home_page_description = view.findViewById(R.id.merchant_home_page_description);
        merchant_home_page_location = view.findViewById(R.id.merchant_home_page_location);
        merchant_home_page_services = view.findViewById(R.id.merchant_home_page_services);
        merchant_home_page_Phone_number = view.findViewById(R.id.merchant_home_page_Phone_number);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        merchant_home_page_services.setLayoutManager(new LinearLayoutManager(getContext()));
        merchant_home_page_services.setHasFixedSize(true);
        adapterServiceView = new AdapterServiceView(merchant.getServices());
        adapterServiceView.setOnServiceClickListener(new AdapterServiceView.OnServiceClickListener() {
            @Override
            public void openNewReservation(View v, Service service, int adapterPosition) {
                callBackFragmentMerchantHomePage.openFragmentNewAppointment(merchant,service);
            }
        });
        merchant_home_page_services.setAdapter(adapterServiceView);
        adapterServiceView.notifyDataSetChanged();
    }

    private void initViews() {
        merchant_page_merchant_name.setText(merchant.getMerchantName());
        if(merchant.getLogo().equals("") || merchant.getLogo() == null) {
            merchant_page_logo.setImageResource(R.drawable.noun_merchant_5111948);
        }
        else {
            MySignal.getInstance().putImgGlide(MyRTFB.getImg(merchant.getLogo()),merchant_page_logo);
        }
        merchant_home_page_description.setText(merchant.getDescription());
        merchant_home_page_Phone_number.setText(merchant.getMerchantPhone());

        MySignal.getInstance().getAddress(merchant.getAddress().getLat(), merchant.getAddress().getLng(), new MySignal.Listener_String() {
            @Override
            public void getString(String str) {
                merchant_home_page_location.setText(str);
            }
        });
    }
}