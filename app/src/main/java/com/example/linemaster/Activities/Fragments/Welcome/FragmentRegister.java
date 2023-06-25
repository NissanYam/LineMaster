package com.example.linemaster.Activities.Fragments.Welcome;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentRegister;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentRegister extends Fragment {
    private AppCompatEditText register_EDT_email;
    private AppCompatEditText register_EDT_firstName;
    private AppCompatEditText register_EDT_lastName;
    private MaterialButton register_BTN_register;
    private CallBackFragmentRegister callBackFragmentRegister;
    private FirebaseAuth mAuth;
    private AppCompatEditText register_EDT_password;
    public FragmentRegister setCallBackFragmentRegister(CallBackFragmentRegister callBackFragmentRegister) {
        this.callBackFragmentRegister = callBackFragmentRegister;
        return this;
    }
    public FragmentRegister() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        register_EDT_email = view.findViewById(R.id.register_EDT_email);
        register_EDT_firstName = view.findViewById(R.id.register_EDT_firstName);
        register_EDT_lastName = view.findViewById(R.id.register_EDT_lastName);
        register_BTN_register = view.findViewById(R.id.register_BTN_register);
        register_EDT_password = view.findViewById(R.id.register_EDT_password);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        register_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_EDT_email.getText().toString().trim();
                String password = register_EDT_password.getText().toString().trim();
                String firstName = register_EDT_firstName.getText().toString().trim();
                String lastName = register_EDT_lastName.getText().toString().trim();
                if(!MySignal.getInstance().isEmail(email)){
                    MySignal.getInstance().playYoyo(register_EDT_email);
                    return;
                } else if (password.isEmpty()) {
                    MySignal.getInstance().playYoyo(register_EDT_password);
                    return;
                }else if(firstName.isEmpty()){
                    MySignal.getInstance().playYoyo(register_EDT_firstName);
                    return;
                } else if (lastName.isEmpty()) {
                    MySignal.getInstance().playYoyo(register_EDT_lastName);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    MySignal.getInstance().toast("Created");
                                    callBackFragmentRegister.RegisterUserSuccessful(email, firstName , lastName);
                                } else {
                                    MySignal.getInstance().toast(task.getException().getMessage());
                                }
                            }
                        });
            }
        });

    }


}
