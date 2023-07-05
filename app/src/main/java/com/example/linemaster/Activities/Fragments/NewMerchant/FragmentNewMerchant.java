package com.example.linemaster.Activities.Fragments.NewMerchant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentNewMerchant;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantDetails;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantPageStyle;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantServices;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantTimes;
import com.example.linemaster.Data.Address;
import com.example.linemaster.Data.BusinessDay;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.Merchant;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FragmentNewMerchant extends Fragment {

    private CallBackFragmentNewMerchant callBackFragmentNewMerchant;
    private MaterialButton new_merchant_BTN_previous;
    private MaterialButton new_merchant_BTN_next;
    private FragmentNewMerchantDetails fragmentNewMerchantDetails;
    private FragmentNewMerchantTimes fragmentNewMerchantTimes;
    private FragmentNewMerchantServices fragmentNewMerchantServices;
    private FragmentNewMerchantPageStyle fragmentNewMerchantPageStyle;
    private CurrentFragmentNewMerchant currentFragmentNewMerchant;
    private Merchant merchant;
    public FragmentNewMerchant() {
    }
    public FragmentNewMerchant setCallBackFragmentNewMerchant(CallBackFragmentNewMerchant callBackFragmentNewMerchant) {
        this.callBackFragmentNewMerchant = callBackFragmentNewMerchant;
        return this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentNewMerchantDetails = new FragmentNewMerchantDetails();
        fragmentNewMerchantDetails.setCallBackFragmentNewMerchantDetails(this.callBackFragmentNewMerchantDetails);
        fragmentNewMerchantTimes = new FragmentNewMerchantTimes();
        fragmentNewMerchantTimes.setCallBackFragmentNewMerchantTimes(this.callBackFragmentNewMerchantTimes);
        fragmentNewMerchantServices = new FragmentNewMerchantServices();
        fragmentNewMerchantServices.setCallBackFragmentNewMerchantServices(this.callBackFragmentNewMerchantServices);
        fragmentNewMerchantPageStyle = new FragmentNewMerchantPageStyle();
        fragmentNewMerchantPageStyle.setCallBackFragmentNewMerchantPageStyle(this.callBackFragmentNewMerchantPageStyle);
        this.currentFragmentNewMerchant = fragmentNewMerchantDetails;
        merchant = new Merchant();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_merchant, container, false);
        findViews(view);
        return view;
    }
    private void findViews(View view) {
        new_merchant_BTN_previous = view.findViewById(R.id.new_merchant_BTN_previous);
        new_merchant_BTN_next = view.findViewById(R.id.new_merchant_BTN_next);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        replaceFragments(fragmentNewMerchantDetails);
        initViews();
        new_merchant_BTN_previous.setVisibility(View.INVISIBLE);
    }
    private void initViews() {
        new_merchant_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant.setOwner(MySignal.getInstance().getUserEmail());
                if (fragmentNewMerchantDetails.equals(currentFragmentNewMerchant)) {
                    if (fragmentNewMerchantDetails.getAllowToContinue()) {
                        MyRTFB.getUser(MySignal.getInstance().getUserEmail(), new MyRTFB.CB_User() {
                            @Override
                            public void getUserData(User user) {
                                if(user.getMerchants() == null)
                                    user.setMerchants(new ArrayList<>());
                                if(!user.getMerchants().contains(fragmentNewMerchantDetails.getMerchant_name())){ /*fill details */
                                    merchant.setMerchantName(fragmentNewMerchantDetails.getMerchant_name())
                                            .setMerchantPhone(fragmentNewMerchantDetails.getMerchant_phone())
                                            .setMerchantType(fragmentNewMerchantDetails.getTypes())
                                            .setDescription(fragmentNewMerchantDetails.getDescription())
                                            .setAddress(new Address()
                                                    .setLat(fragmentNewMerchantDetails.getLat())
                                                    .setLng(fragmentNewMerchantDetails.getLng()));
                                    new_merchant_BTN_previous.setVisibility(View.VISIBLE);
                                    currentFragmentNewMerchant = fragmentNewMerchantTimes;
                                    replaceFragments(fragmentNewMerchantTimes);
                                }else {
                                    MySignal.getInstance().toast("The merchant name : ".concat(fragmentNewMerchantDetails.getMerchant_name()).concat(" already exist"));
                                }
                            }
                        });
                    }
                } else if (fragmentNewMerchantTimes.equals(currentFragmentNewMerchant)) {
                    if (fragmentNewMerchantTimes.getAllowToContinue()) {
                        ArrayList<BusinessDay> businessDays = (ArrayList<BusinessDay>) new ArrayList<>(fragmentNewMerchantTimes.getDayOfWeekBusinessDayHashMap().values());
                        merchant.setBusinessDays(businessDays);
                        currentFragmentNewMerchant = fragmentNewMerchantServices;
                        replaceFragments(fragmentNewMerchantServices);
                    }
                } else if (fragmentNewMerchantServices.equals(currentFragmentNewMerchant)) {
                    if (fragmentNewMerchantServices.getAllowToContinue()) {
                        merchant.setServices(fragmentNewMerchantServices.getServices());
                        currentFragmentNewMerchant = fragmentNewMerchantPageStyle;
                        replaceFragments(fragmentNewMerchantPageStyle);
                        new_merchant_BTN_next.setText(R.string.Done);
                    }
                } else if (fragmentNewMerchantPageStyle.equals(currentFragmentNewMerchant)) {
                    if (fragmentNewMerchantPageStyle.getAllowToContinue()) {
                        if(fragmentNewMerchantPageStyle.getUriImage() == null){
                            merchant.setLogo("");
                        }else{
                            merchant.setLogo(fragmentNewMerchantPageStyle.getUriImage().toString());
                        }
                        merchant.setJournal(new Journal().setAppointments(new ArrayList<>()));
                        callBackFragmentNewMerchant.theNewMerchantDone(merchant);
                    }
                }
            }
        });
        new_merchant_BTN_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentNewMerchantTimes.equals(currentFragmentNewMerchant)) {
                    fragmentNewMerchantTimes.clearAll();
                    currentFragmentNewMerchant = fragmentNewMerchantDetails;
                    new_merchant_BTN_previous.setVisibility(View.INVISIBLE);
                    replaceFragments(fragmentNewMerchantDetails);

                } else if (fragmentNewMerchantServices.equals(currentFragmentNewMerchant)) {
                    fragmentNewMerchantServices.clearAll();
                    currentFragmentNewMerchant = fragmentNewMerchantTimes;
                    replaceFragments(fragmentNewMerchantTimes);

                } else if (fragmentNewMerchantPageStyle.equals(currentFragmentNewMerchant)) {
                    fragmentNewMerchantPageStyle.clearAll();
                    currentFragmentNewMerchant = fragmentNewMerchantServices;
                    replaceFragments(fragmentNewMerchantServices);
                    new_merchant_BTN_next.setText(R.string.Next);
                }
            }
        });
    }
    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.new_merchant_FL, fragment);
        fragmentTransaction.commit();
    }
    private CallBackFragmentNewMerchantDetails callBackFragmentNewMerchantDetails = new CallBackFragmentNewMerchantDetails() {
    };
    private CallBackFragmentNewMerchantTimes callBackFragmentNewMerchantTimes = new CallBackFragmentNewMerchantTimes() {
    };
    private CallBackFragmentNewMerchantServices callBackFragmentNewMerchantServices = new CallBackFragmentNewMerchantServices() {
    };
    private CallBackFragmentNewMerchantPageStyle callBackFragmentNewMerchantPageStyle = new CallBackFragmentNewMerchantPageStyle() {
    };
}