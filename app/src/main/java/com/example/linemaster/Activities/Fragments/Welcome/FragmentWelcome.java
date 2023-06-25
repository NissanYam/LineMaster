package com.example.linemaster.Activities.Fragments.Welcome;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.linemaster.Activities.Callbacks.CallBackFragmentWelcome;
import com.example.linemaster.R;

public class FragmentWelcome extends Fragment {
    private CardView welcome_register;
    private CardView welcome_login;
    private CallBackFragmentWelcome callBackFragmentWelcome;
    public FragmentWelcome() {
    }
    public FragmentWelcome setCallBackFragmentWelcome(CallBackFragmentWelcome callBackFragmentWelcome) {
        this.callBackFragmentWelcome = callBackFragmentWelcome;
        return this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        findViews(view);
        return view;
    }
    private void findViews(View view) {
        welcome_register = view.findViewById(R.id.welcome_register);
        welcome_login = view.findViewById(R.id.welcome_login);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }
    private void initViews() {
        welcome_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackFragmentWelcome.goToLogInFragment();
            }
        });
        welcome_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackFragmentWelcome.goToLRegisterFragment();
            }
        });
    }
}
