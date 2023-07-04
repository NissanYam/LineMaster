package com.example.linemaster.Activities.Fragments.NewMerchant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentMap;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantDetails;
import com.example.linemaster.Data.MerchantType;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;


public class FragmentNewMerchantDetails extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentNewMerchantDetails callBackFragmentNewMerchantDetails;
    private EditText new_merchant_details_EDT_merchant_name;
    private EditText new_merchant_details_EDT_merchant_phone;
    private FragmentMap fragmentMap;
    private double lat = 0.0 ,lng = 0.0;
    private String merchant_name;
    private String merchant_phone;
    private Chip new_merchant_details_chip_spa;
    private Chip new_merchant_details_chip_nails;
    private Chip new_merchant_details_chip_privetLesson;
    private Chip new_merchant_details_chip_makeup;
    private Chip new_merchant_details_chip_barbar;
    private Chip new_merchant_details_chip_gym;
    private TextInputEditText new_merchant_details_description;
    private ArrayList<Chip> chipsClicked;
    public FragmentNewMerchantDetails() {
    }

    public FragmentNewMerchantDetails setCallBackFragmentNewMerchantDetails(CallBackFragmentNewMerchantDetails callBackFragmentNewMerchantDetails) {
        this.callBackFragmentNewMerchantDetails = callBackFragmentNewMerchantDetails;
        return this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_merchant_details, container, false);
        fragmentMap = new FragmentMap();
        fragmentMap.setCallBackFragmentMap(this.callBackFragmentMap);
        findViews(view);
        chipsClicked = new ArrayList<>();
        return view;
    }


    private void findViews(View view) {
        new_merchant_details_EDT_merchant_name = view.findViewById(R.id.new_merchant_details_EDT_merchant_name);
        new_merchant_details_EDT_merchant_phone = view.findViewById(R.id.new_merchant_details_EDT_merchant_phone);
        new_merchant_details_chip_spa = view.findViewById(R.id.new_merchant_details_chip_spa);
        new_merchant_details_chip_nails = view.findViewById(R.id.new_merchant_details_chip_nails);
        new_merchant_details_chip_privetLesson = view.findViewById(R.id.new_merchant_details_chip_privetLesson);
        new_merchant_details_chip_makeup = view.findViewById(R.id.new_merchant_details_chip_makeup);
        new_merchant_details_chip_barbar = view.findViewById(R.id.new_merchant_details_chip_barbar);
        new_merchant_details_chip_gym = view.findViewById(R.id.new_merchant_details_chip_gym);
        new_merchant_details_description = view.findViewById(R.id.new_merchant_details_description);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        replaceFragments(fragmentMap);
        initViews();
    }

    private void initViews() {
        new_merchant_details_chip_spa.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_spa));
        new_merchant_details_chip_nails.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_nails));
        new_merchant_details_chip_privetLesson.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_privetLesson));
        new_merchant_details_chip_makeup.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_makeup));
        new_merchant_details_chip_barbar.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_barbar));
        new_merchant_details_chip_gym.setOnClickListener(v -> changeColorChip(new_merchant_details_chip_gym));
    }

    private void changeColorChip(Chip chip_changed) {
        if (this.chipsClicked.contains(chip_changed)) {
            chip_changed.setChipBackgroundColorResource(R.color.white);
            this.chipsClicked.remove(chip_changed);
        } else {
            chip_changed.setChipBackgroundColorResource(R.color.LeadingColor);
            this.chipsClicked.add(chip_changed);
        }
    }

    /**
     * check all fields in the layout
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean getAllowToContinue() {
        if (new_merchant_details_EDT_merchant_name.getText().toString().replaceAll(" ", "").isEmpty() ||
                new_merchant_details_EDT_merchant_name.getText().toString() == null) {
            new_merchant_details_EDT_merchant_name.setHintTextColor(R.color.red);
            MySignal.getInstance().playYoyo(new_merchant_details_EDT_merchant_name);
            return false;
        } else if (new_merchant_details_EDT_merchant_phone.getText().toString().replaceAll(" ", "").isEmpty() ||
                new_merchant_details_EDT_merchant_phone.getText().toString() == null) {
            new_merchant_details_EDT_merchant_phone.setHintTextColor(R.color.red);
            MySignal.getInstance().playYoyo(new_merchant_details_EDT_merchant_phone);
            return false;
        } else if(lat == 0.0 || lng == 0.0){
            MySignal.getInstance().toast("Enter Location");
            return false;
        }
        new_merchant_details_EDT_merchant_phone.setHintTextColor(R.color.black);
        new_merchant_details_EDT_merchant_name.setHintTextColor(R.color.black);
        setMerchantName(new_merchant_details_EDT_merchant_name.getText().toString());
        setMerchantPhone(new_merchant_details_EDT_merchant_phone.getText().toString());
        return true;
    }

    private void setMerchantPhone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    private void setMerchantName(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public ArrayList<MerchantType> getTypes() {
        ArrayList<MerchantType> merchantTypes= new ArrayList<>();
        merchantTypes.add(MerchantType.Any);
        for (Chip chip: this.chipsClicked) {
            if(chip.equals(new_merchant_details_chip_spa)){
                merchantTypes.add(MerchantType.Spa);
            }
            if(chip.equals(new_merchant_details_chip_nails)){
                merchantTypes.add(MerchantType.Nails);
            }
            if(chip.equals(new_merchant_details_chip_privetLesson)){
                merchantTypes.add(MerchantType.PrivateLesson);
            }
            if(chip.equals(new_merchant_details_chip_makeup)){
                merchantTypes.add(MerchantType.Makeup);
            }
            if(chip.equals(new_merchant_details_chip_barbar)){
                merchantTypes.add(MerchantType.Barbershop);
            }
            if(chip.equals(new_merchant_details_chip_gym)){
                merchantTypes.add(MerchantType.Sport);
            }
        }
        return merchantTypes;
    }

    /**
     * clear all fields
     **/
    @Override
    public void clearAll() {

        new_merchant_details_EDT_merchant_name.setText("");
        new_merchant_details_EDT_merchant_phone.setText("");
        new_merchant_details_chip_spa.setClickable(false);
        new_merchant_details_chip_nails.setClickable(false);
        new_merchant_details_chip_privetLesson.setClickable(false);
        new_merchant_details_chip_makeup.setClickable(false);
        new_merchant_details_chip_barbar.setClickable(false);
        new_merchant_details_chip_gym.setClickable(false);
        new_merchant_details_description.setText("");
        chipsClicked = new ArrayList<>();
    }
    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.new_merchant_details_map_and_address, fragment);
        fragmentTransaction.commit();
    }
    private CallBackFragmentMap callBackFragmentMap = new CallBackFragmentMap() {
        @Override
        public void sendLatLng(LatLng latLng) {
            lat = latLng.latitude;
            lng = latLng.longitude;
            MySignal.getInstance().getAddress(lat, lng, new MySignal.Listener_String() {
                @Override
                public void getString(String str) {
                    MySignal.getInstance().toast(str);
                }
            });

        }
    };

    public String getDescription() {
        return new_merchant_details_description.getText().toString();
    }
}




