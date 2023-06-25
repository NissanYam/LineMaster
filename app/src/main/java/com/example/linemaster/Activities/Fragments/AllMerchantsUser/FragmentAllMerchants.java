package com.example.linemaster.Activities.Fragments.AllMerchantsUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentAllMerchants;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.R;

import java.util.ArrayList;


public class FragmentAllMerchants extends Fragment {

    private CallBackFragmentAllMerchants callBackFragmentAllMerchants;
    private ArrayList<Merchant> merchants;
    private RecyclerView recyclerview_list_my_business;
    private AdapterAllMerchantsUser adapterAllMerchantsUser;
    public FragmentAllMerchants() {
    }

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public FragmentAllMerchants setMerchants(ArrayList<Merchant> merchants) {
        this.merchants = merchants;
        return this;
    }

    public FragmentAllMerchants setCallBackFragmentAllMerchants(CallBackFragmentAllMerchants callBackFragmentAllMerchants) {
        this.callBackFragmentAllMerchants = callBackFragmentAllMerchants;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_merchantst, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        recyclerview_list_my_business = view.findViewById(R.id.recyclerview_list_my_business);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview_list_my_business.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_list_my_business.setHasFixedSize(true);

        adapterAllMerchantsUser = new AdapterAllMerchantsUser();
        adapterAllMerchantsUser.setMerchants(merchants);

        recyclerview_list_my_business.setAdapter(adapterAllMerchantsUser);
        adapterAllMerchantsUser.notifyDataSetChanged();
        initViews();
    }
    private void initViews() {
        adapterAllMerchantsUser.setOnMerchantClickListener(new AdapterAllMerchantsUser.OnMerchantClickListener() {
            @Override
            public void openMerchant(View v, Merchant item, int adapterPosition) {
                callBackFragmentAllMerchants.openMerchantPage(item);
            }
        });
    }
}