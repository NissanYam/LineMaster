package com.example.linemaster.Activities.Fragments.NewMerchant;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.linemaster.Activities.Fragments.NewMerchant.CallbacksMerchant.CallBackFragmentNewMerchantPageStyle;
import com.example.linemaster.R;


public class FragmentNewMerchantPageStyle extends Fragment implements CurrentFragmentNewMerchant {

    private CallBackFragmentNewMerchantPageStyle callBackFragmentNewMerchantPageStyle;
    private ImageView new_merchant_page_style_logoIMG, new_merchant_page_style_logoIMG_gallery;
    private Uri uriImage;

    public Uri getUriImage() {
        return uriImage;
    }

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
        return view;
    }

    private void findViews(View view) {
        new_merchant_page_style_logoIMG = view.findViewById(R.id.new_merchant_page_style_logoIMG);
        new_merchant_page_style_logoIMG_gallery = view.findViewById(R.id.new_merchant_page_style_logoIMG_gallery);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        new_merchant_page_style_logoIMG_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
    }
    @Override
    public boolean getAllowToContinue() {
        return true;
    }

    @Override
    public void clearAll() {
        new_merchant_page_style_logoIMG.setImageResource(R.drawable.noun_merchant_5111948);
    }

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                uriImage = data.getData();
                if (null != uriImage) {
                    // update the preview image in the layout
                    new_merchant_page_style_logoIMG.setImageURI(uriImage);
                }
            }
        }
    }
}
