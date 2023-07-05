package com.example.linemaster.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentHome;
import com.example.linemaster.Activities.Fragments.AllMerchantsUser.AdapterAllMerchantsUser;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.MerchantType;
import com.example.linemaster.R;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private CallBackFragmentHome callBackFragmentHome;
    private MaterialButton home_BTN_gym;
    private MaterialButton home_BTN_barbar;
    private MaterialButton home_BTN_makeup;
    private MaterialButton home_BTN_nails;
    private MaterialButton home_BTN_spa;
    private MaterialButton home_BTN_all;
    private MaterialButton home_BTN_privetLesson;
    private RecyclerView recyclerview_list_business;
    private AdapterAllMerchantsUser adapterAllMerchantsUser;
    private ArrayList<Merchant> merchants;

    public FragmentHome() {
    }
    public void setMerchants(ArrayList<Merchant> merchants) {
        this.merchants = merchants;
    }
    public void setCallBackFragmentHome(CallBackFragmentHome callBackFragmentHome) {
        this.callBackFragmentHome = callBackFragmentHome;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        return view;
    }
    private void findViews(View view) {
        home_BTN_gym = view.findViewById(R.id.home_BTN_gym);
        home_BTN_barbar = view.findViewById(R.id.home_BTN_barbar);
        home_BTN_makeup = view.findViewById(R.id.home_BTN_makeup);
        home_BTN_nails = view.findViewById(R.id.home_BTN_nails);
        home_BTN_spa = view.findViewById(R.id.home_BTN_spa);
        home_BTN_all = view.findViewById(R.id.home_BTN_all);
        home_BTN_privetLesson = view.findViewById(R.id.home_BTN_privetLesson);
        recyclerview_list_business = view.findViewById(R.id.recyclerview_list_business);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview_list_business.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_list_business.setHasFixedSize(true);

        adapterAllMerchantsUser = new AdapterAllMerchantsUser();
        if(merchants != null)
            adapterAllMerchantsUser.setMerchants(merchants);
        else
            adapterAllMerchantsUser.setMerchants(new ArrayList<>());
        recyclerview_list_business.setAdapter(adapterAllMerchantsUser);
        adapterAllMerchantsUser.notifyDataSetChanged();
        initViews();
    }

    public void refreshData(){
        adapterAllMerchantsUser.setMerchants(merchants);
        adapterAllMerchantsUser.notifyDataSetChanged();
    }

    private void initViews() {
        home_BTN_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Sport));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_barbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Barbershop));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Makeup));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_nails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Nails));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_spa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Spa));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.Any));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        home_BTN_privetLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAllMerchantsUser.setMerchants(getAllMerchantByType(MerchantType.PrivateLesson));
                adapterAllMerchantsUser.notifyDataSetChanged();
            }
        });
        adapterAllMerchantsUser.setOnMerchantClickListener(new AdapterAllMerchantsUser.OnMerchantClickListener() {
            @Override
            public void openMerchant(View v, Merchant item, int adapterPosition) {
                callBackFragmentHome.openMerchantPage(item);
            }
        });
    }

    private List<Merchant> getAllMerchantByType(MerchantType merchantType) {
        ArrayList<Merchant> rv = new ArrayList<>();
        if(merchantType == MerchantType.Any){
            return this.merchants;
        }
        for (Merchant merchant : this.merchants) {
            for (MerchantType merchantType1 : merchant.getMerchantType()) {
                if (merchantType1 == merchantType) {
                    rv.add(merchant);
                    break;
                }
            }
        }
        return rv;
    }
}
