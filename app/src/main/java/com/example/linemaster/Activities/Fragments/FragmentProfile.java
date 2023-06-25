package com.example.linemaster.Activities.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.linemaster.Activities.Callbacks.CallBackFragmentProfile;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

public class FragmentProfile extends Fragment {

    private CallBackFragmentProfile callBackFragmentProfile;

    private ScrollView profile_view;
    private ImageView profile_profile_img;
    private TextView profile_full_name;
    private TextView profile_edit_profile;
    private TextView profile_phone_number;
    private TextView profile_email;
    private ImageView profile_edit_profile_img;
    private EditText profile_edit_first_name;
    private EditText profile_edit_last_name;
    private TextView profile_exit_edit_profile;
    private TextView profile_edit_phone_number;
    private TextView profile_edit_email;
    private ScrollView profile_edit;
    private User user;
    private Bitmap logoImage;

    public FragmentProfile() {
    }

    public FragmentProfile setCallBackFragmentProfile(CallBackFragmentProfile callBackFragmentProfile) {
        this.callBackFragmentProfile = callBackFragmentProfile;
        return this;
    }

    public User getUser() {
        return user;
    }

    public FragmentProfile setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        checkPermissions();
        return view;
    }

    private void findViews(View view) {
        profile_view = view.findViewById(R.id.profile_view);
        profile_profile_img = view.findViewById(R.id.profile_profile_img);
        profile_full_name = view.findViewById(R.id.profile_full_name);
        profile_edit_profile = view.findViewById(R.id.profile_edit_profile);
        profile_phone_number = view.findViewById(R.id.profile_phone_number);
        profile_email = view.findViewById(R.id.profile_email);

        profile_edit_profile_img = view.findViewById(R.id.profile_edit_profile_img);
        profile_edit_first_name = view.findViewById(R.id.profile_edit_first_name);
        profile_edit_last_name = view.findViewById(R.id.profile_edit_last_name);
        profile_exit_edit_profile = view.findViewById(R.id.profile_exit_edit_profile);
        profile_edit_phone_number = view.findViewById(R.id.profile_edit_phone_number);
        profile_edit_email = view.findViewById(R.id.profile_edit_email);
        profile_edit = view.findViewById(R.id.profile_edit);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValues();
        initViews();
    }

    private void setValues() {
        if (!user.getPicture().equals("0")) {
            profile_profile_img.setImageBitmap(MySignal.getInstance().StringToBitMap(user.getPicture()));
        } else {
            profile_profile_img.setImageResource(R.drawable.profile_round_1342_svgrepo_com);
        }
        profile_full_name.setText(user.getFirstName()+" "+user.getLastName());
        profile_phone_number.setText(user.getPhoneNumber());
        profile_email.setText(user.getEmail());
    }

    private void initViews() {
        profile_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_view.setVisibility(View.GONE);
                profile_edit.setVisibility(View.VISIBLE);
                if (!user.getPicture().equals("0")) {
                    profile_edit_profile_img.setImageBitmap(MySignal.getInstance().StringToBitMap(user.getPicture()));
                } else {
                    profile_edit_profile_img.setImageResource(R.drawable.profile_round_1342_svgrepo_com);
                }
                profile_edit_first_name.setText(user.getFirstName());
                profile_edit_last_name.setText(user.getLastName());
                profile_edit_phone_number.setText(user.getPhoneNumber());
                profile_edit_email.setText(user.getEmail());
            }
        });
        profile_exit_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFirstName(profile_edit_first_name.getText().toString());
                user.setLastName(profile_edit_last_name.getText().toString());
                user.setPhoneNumber(profile_edit_phone_number.getText().toString());
                setValues();
                MyRTFB.updateUser(user);
                profile_view.setVisibility(View.VISIBLE);
                profile_edit.setVisibility(View.GONE);

            }
        });
        profile_edit_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startCamera.launch(cameraIntent);
            }
        });
    }
    private ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap image = (Bitmap) extras.get("data");
                    logoImage = image;
                    ImageView profileImage = profile_edit_profile_img;
                    profileImage.setImageBitmap(image);
                    profileImage.setPadding(4, 4, 4, 4); // Set padding if needed
                    profileImage.invalidate(); // Invalidate the view to reflect the changes
                    user.setPicture(MySignal.getInstance().BitMapToString(logoImage));
                }
            });
    private void checkPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{android.Manifest.permission.CAMERA},
                333);
    }
}
