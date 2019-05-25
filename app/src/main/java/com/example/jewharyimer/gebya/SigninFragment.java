package com.example.jewharyimer.gebya;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.jewharyimer.gebya.RegisterActivity.onRestPasswordFragment;


public class SigninFragment extends Fragment {



     private TextView dontHaveAccount;
     private FrameLayout parentFrameLayout;

     private EditText email,password;
     private Button signinbtn;
     private ImageButton closebtn;

     private TextView forgotpassword;

     private FirebaseAuth auth;
     private ProgressBar prgbar;
    public static boolean Disableclosebtn=false;

    public SigninFragment() {

    }
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View view=inflater.inflate(R.layout.fragment_signin, container, false);
                dontHaveAccount=view.findViewById(R.id.no_account);
                parentFrameLayout=getActivity().findViewById(R.id.register_framlayout);

                email=view.findViewById(R.id.signin_email);
                password=view.findViewById(R.id.signin_name);

                closebtn=view.findViewById(R.id.signin_close);
                signinbtn=view.findViewById(R.id.signin_button);

                prgbar=view.findViewById(R.id.signin_prgBar);

                auth=FirebaseAuth.getInstance();
                forgotpassword=view.findViewById(R.id.signin_forgot);

                if(Disableclosebtn){
                    closebtn.setVisibility(View.GONE);
                }else {
                    closebtn.setVisibility(View.VISIBLE);
                }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignupFragment());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestPasswordFragment = true;
               // Intent forgotintent=new Intent(getActivity(),)
                setFragment(new ReserPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPassword();
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
    }

    private void mainIntent() {
        if(Disableclosebtn)
        {
            Disableclosebtn=false;
        }else {
            Intent mainintent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainintent);
        }
        getActivity().finish();
    }

    private void checkEmailandPassword() {
        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            if(password.getText().length()>=8){
                prgbar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.argb(50,255,255,255));
                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                    mainIntent();

               }else {
                   prgbar.setVisibility(View.INVISIBLE);
                   signinbtn.setEnabled(true);
                   signinbtn.setTextColor(Color.rgb(255,255,255));

                   String msg=task.getException().getMessage();
                   Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
               }
                }
            });
            }else {
                Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText().toString())){
            if(!TextUtils.isEmpty(password.getText())){
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.rgb(255,255,255));
            }else {
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);

        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
