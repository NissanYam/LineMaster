package com.example.linemaster.Activities.Fragments.NewMerchant;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantPageStyle;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;


public class FragmentNewMerchantPageStyle extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentNewMerchantPageStyle callBackFragmentNewMerchantPageStyle;
    private ImageView new_merchant_page_style_logoIMG;
    private Bitmap logoImage;


    public FragmentNewMerchantPageStyle() {
    }

    public FragmentNewMerchantPageStyle setCallBackFragmentNewMerchantPageStyle(CallBackFragmentNewMerchantPageStyle callBackFragmentNewMerchantPageStyle) {
        this.callBackFragmentNewMerchantPageStyle = callBackFragmentNewMerchantPageStyle;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_merchant_page_style, container, false);
        findViews(view);
        checkPermissions();
        return view;
    }

    private void findViews(View view) {
        new_merchant_page_style_logoIMG = view.findViewById(R.id.new_merchant_page_style_logoIMG);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        new_merchant_page_style_logoIMG.setOnClickListener(new View.OnClickListener() {
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

                    ImageView profileImage = new_merchant_page_style_logoIMG;
                    profileImage.setImageBitmap(image);
                    profileImage.setPadding(4, 4, 4, 4); // Set padding if needed
                    profileImage.invalidate(); // Invalidate the view to reflect the changes
                }
            });

    private void checkPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{android.Manifest.permission.CAMERA},
                333);
    }
    private static final String DEFULT = "0";
    public void setImgProfile(String bitmap){
        if(bitmap.equals(DEFULT)){
            new_merchant_page_style_logoIMG.setImageResource(R.drawable.noun_merchant_5111948);
        }else {
            new_merchant_page_style_logoIMG.setImageBitmap(MySignal.getInstance().StringToBitMap(bitmap));;///TODO: get the merchant logo
        }
    }
    @Override
    public boolean getAllowToContinue() {
        return true;
    }

    @Override
    public void clearAll() {
        new_merchant_page_style_logoIMG.setImageResource(R.drawable.noun_merchant_5111948);
    }

    public Bitmap getLogoImage() {
        return logoImage;
    }

}
