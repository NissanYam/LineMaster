package com.example.linemaster.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentLogin;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentRegister;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentWelcome;
import com.example.linemaster.Activities.Fragments.Welcome.FragmentLogin;
import com.example.linemaster.Activities.Fragments.Welcome.FragmentRegister;
import com.example.linemaster.Activities.Fragments.Welcome.FragmentWelcome;
import com.example.linemaster.Data.Journal;
import com.example.linemaster.Data.User;
import com.example.linemaster.MyRTFB;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

import java.util.ArrayList;


public class LoginRegisterActivity extends AppCompatActivity {
    private FragmentLogin fragmentLogin;
    private FragmentRegister fragmentRegister;
    private FragmentWelcome fragmentWelcome;
    private ImageView loginRegister_IMG_return;
    private ImageView loginRegister_IMG_language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        findViews();
        initViews();
        fragmentWelcome = new FragmentWelcome();
        fragmentWelcome.setCallBackFragmentWelcome(this.callBackFragmentWelcome);
        fragmentLogin = new FragmentLogin();
        fragmentLogin.setCallBackFragmentLogin(this.callBackFragmentLogin);
        fragmentRegister = new FragmentRegister();
        fragmentRegister.setCallBackFragmentRegister(this.callBackFragmentRegister);
        loginRegister_IMG_return.setVisibility(View.INVISIBLE);
        replaceFragments(fragmentWelcome);

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initViews() {
        loginRegister_IMG_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginRegister_IMG_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(fragmentWelcome);
                loginRegister_IMG_return.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void findViews() {
        loginRegister_IMG_return = findViewById(R.id.loginRegister_IMG_return);
        loginRegister_IMG_language = findViewById(R.id.loginRegister_IMG_language);
    }
    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_register_LAY, fragment);
        fragmentTransaction.commit();
    }
    private CallBackFragmentLogin callBackFragmentLogin = new CallBackFragmentLogin() {

        @Override
        public void LogInUserSuccessful(String email) {
            MySignal.getInstance().toast(email);
            MySignal.getInstance().setUserEmail(email);
            /**Change to next intent*/
            nextIntent();
        }

    };
    private CallBackFragmentRegister callBackFragmentRegister = new CallBackFragmentRegister(){
        @Override
        public void RegisterUserSuccessful(String email , String firstName, String lastName) {
            //Need to check with DB if the user exist!
            //Sign up user
            /**Change to next intent*/
            MyRTFB.saveNewUser(new User().
                    setEmail(email).
                    setFirstName(firstName).
                    setLastName(lastName).
                    setMerchants(new ArrayList<>()).
                    setPicture("0").
                    setPersonalJournal(new Journal().setAppointments(new ArrayList<>())).
                    setPhoneNumber(""));
            MySignal.getInstance().setUserEmail(email);
            nextIntent();
        }
    };
    private void nextIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private CallBackFragmentWelcome callBackFragmentWelcome = new CallBackFragmentWelcome() {
        @Override
        public void goToLogInFragment() {
            replaceFragments(fragmentLogin);
            loginRegister_IMG_return.setVisibility(View.VISIBLE);
        }
        @Override
        public void goToLRegisterFragment() {
            replaceFragments(fragmentRegister);
            loginRegister_IMG_return.setVisibility(View.VISIBLE);
        }
    };

}
