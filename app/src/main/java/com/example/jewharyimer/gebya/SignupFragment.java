package com.example.jewharyimer.gebya;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignupFragment extends Fragment {


    public SignupFragment() {

    }
    private TextView alreadyhaveaccount;
    private FrameLayout parentFrameLayout;

    private EditText email,fulname,password,confirm;
    private ImageButton closeButton;
    private Button signupButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebasefirestore;

    public static boolean disableCloseBtn=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup, container, false);

        alreadyhaveaccount=view.findViewById(R.id.have_accpunt);
        email=view.findViewById(R.id.signup_email);
        fulname=view.findViewById(R.id.signup_name);
        password=view.findViewById(R.id.signup_password);
        confirm=view.findViewById(R.id.signup_confirm);

        closeButton=view.findViewById(R.id.signup_close);
        signupButton=view.findViewById(R.id.signup_button);
        progressBar=view.findViewById(R.id.signup_prgBar);

        firebaseAuth=FirebaseAuth.getInstance();
        firebasefirestore=FirebaseFirestore.getInstance();
        if(disableCloseBtn){
            closeButton.setVisibility(View.GONE);
        }else {
            closeButton.setVisibility(View.VISIBLE);
        }

        parentFrameLayout=getActivity().findViewById(R.id.register_framlayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
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
        fulname.addTextChangedListener(new TextWatcher() {
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
        confirm.addTextChangedListener(new TextWatcher() {
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

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             checkEmailAddress();
            }
        });
    }

    private void mainIntent() {
        if(disableCloseBtn){
            disableCloseBtn=false;
        }else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
         getActivity().finish();
    }

    private void checkEmailAddress() {
        Drawable customErrorIcon=getResources().getDrawable(R.drawable.ic_priority_high_black_24dp);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
        if(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            if(password.getText().toString().equals(confirm.getText().toString())){
                progressBar.setVisibility(getView().VISIBLE);
                  firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Map<Object,String> userdata=new HashMap <>();
                                 userdata.put("fulname",fulname.getText().toString());
                                 firebasefirestore.collection("USERS").add(userdata)
                                         .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                             @Override
                                             public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                mainIntent();
                                            }else {
                                                String msg=task.getException().getMessage();
                                                progressBar.setVisibility(View.INVISIBLE);
                                                signupButton.setEnabled(false);
                                                signupButton.setTextColor(Color.argb(50,255,255,255));
                                                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                                            }
                                             }
                                         });

                             }else {
                                 String msg=task.getException().getMessage();
                                 progressBar.setVisibility(View.INVISIBLE);
                                 signupButton.setEnabled(false);
                                 signupButton.setTextColor(Color.argb(50,255,255,255));
                                 Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                             }


                              }
                          });

            }else {
                confirm.setError("password dosn't matched",customErrorIcon);

            }
        }else{
            email.setError(" Invalid Email",customErrorIcon);

        }
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fulname.getText())){

                if(!TextUtils.isEmpty(password.getText()) && password.length()>=8){

                    if(!TextUtils.isEmpty(confirm.getText())){
                        signupButton.setEnabled(true);
                        signupButton.setTextColor(Color.rgb(255,255,255));
                    }else {
                        signupButton.setEnabled(false);
                        signupButton.setTextColor(Color.argb(50,255,255,255));
                    }
                } else {
                    signupButton.setEnabled(false);
                    signupButton.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signupButton.setEnabled(false);
                signupButton.setTextColor(Color.argb(50,255,255,255));
            }

        }else{
         signupButton.setEnabled(false);
         signupButton.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }}

