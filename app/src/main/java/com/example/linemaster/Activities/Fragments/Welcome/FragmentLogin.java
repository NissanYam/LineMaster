package com.example.linemaster.Activities.Fragments.Welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.linemaster.Activities.Callbacks.CallBackFragmentLogin;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentLogin extends Fragment {
    private MaterialButton login_BTN_login;
    private AppCompatEditText login_EDT_email;
    private AppCompatEditText login_EDT_password;
    private CallBackFragmentLogin callBackFragmentLogin;
    private FirebaseAuth mAuth;

    private ImageView login_IMG_facebook,
                        login_IMG_google,
                        login_IMG_twitter;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    public FragmentLogin() {
    }

    public FragmentLogin setCallBackFragmentLogin(CallBackFragmentLogin callBackFragmentLogin) {
        this.callBackFragmentLogin = callBackFragmentLogin;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getContext(), gso);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            callBackFragmentLogin.LogInUserSuccessful(currentUser.getEmail());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        login_BTN_login = view.findViewById(R.id.login_BTN_login);
        login_EDT_email = view.findViewById(R.id.login_EDT_email);
        login_EDT_password = view.findViewById(R.id.login_EDT_password);
        login_IMG_facebook = view.findViewById(R.id.login_IMG_facebook);
        login_IMG_google = view.findViewById(R.id.login_IMG_google);
        login_IMG_twitter = view.findViewById(R.id.login_IMG_twitter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_EDT_email.getText().toString().trim();
                String password = login_EDT_password.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    callBackFragmentLogin.LogInUserSuccessful(email);
                                } else {
                                    MySignal.getInstance().playYoyo(login_EDT_email);
                                }
                            }
                        });
            }
        });

        login_IMG_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        login_IMG_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        login_IMG_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
