package com.example.linemaster.Activities.Fragments.NewMerchant;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentMap;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

public class FragmentMap extends Fragment implements OnMapReadyCallback,CurrentFragmentNewMerchant {
    private GoogleMap googleMap;
    private SearchView sv_places;
    private SupportMapFragment supportMapFragment;

    private CallBackFragmentMap callBackFragmentMap;

    public  FragmentMap(){}

    public FragmentMap setCallBackFragmentMap(CallBackFragmentMap callBackFragmentMap) {
        this.callBackFragmentMap = callBackFragmentMap;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        this.supportMapFragment = supportMapFragment;
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        sv_places = view.findViewById(R.id.sv_places);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        sv_places.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                googleMap.clear();
                String location = sv_places.getQuery().toString();
                List<Address> addresses = null;
                Geocoder geocoder = new Geocoder(getContext());
                try{
                    addresses = geocoder.getFromLocationName(location,1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Address address = null;
                if(addresses.size()>0){
                    address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    callBackFragmentMap.sendLatLng(latLng);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }else {
                    MySignal.getInstance().toast("Not found");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng centerMapLocation = new LatLng(0,0);
        setCameraPosition(centerMapLocation);
    }
    private void setCameraPosition(LatLng myLocation) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myLocation)
                .zoom(0)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean getAllowToContinue() {
        return false;
    }

    @Override
    public void clearAll() {

    }
}