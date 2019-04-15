package com.example.jewharyimer.gebya;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReserPasswordFragment extends Fragment {


    private EditText registerdemail;
    private Button reserButton;
    private TextView goback;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private ProgressBar prgbar;
    private TextView emailIconText;

    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    public ReserPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reser_password, container, false);

        registerdemail=view.findViewById(R.id.reset_email);
        reserButton=view.findViewById(R.id.reset_button);
        goback=view.findViewById(R.id.reset_goback);

        emailIcon=view.findViewById(R.id.email_Icon);
        emailIconContainer=view.findViewById(R.id.email_Icon_container);
        prgbar=view.findViewById(R.id.rsprogressBar);
        emailIconText=view.findViewById(R.id.forgot_password_Text);

        parentFrameLayout=getActivity().findViewById(R.id.register_framlayout);
        firebaseAuth=FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerdemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        reserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                prgbar.setVisibility(View.VISIBLE);


                registerdemail.setEnabled(false);
                registerdemail.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registerdemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){

                                 ScaleAnimation scaleAnimation=new ScaleAnimation(1,0,1,emailIconContainer.getY());
                                 scaleAnimation.setDuration(100);
                                 scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                 scaleAnimation.setRepeatMode(Animation.REVERSE);
                                 scaleAnimation.setRepeatCount(1);
                                 scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                     @Override
                                     public void onAnimationStart(Animation animation) {

                                     }

                                     @Override
                                     public void onAnimationEnd(Animation animation) {

                                     }

                                     @Override
                                     public void onAnimationRepeat(Animation animation) {

                                     }
                                 });
                                 emailIcon.startAnimation(scaleAnimation);
                                 Toast.makeText(getActivity(),"Email sent successfully",Toast.LENGTH_SHORT).show();

                             } else {
                                 registerdemail.setEnabled(true);
                                 registerdemail.setTextColor(Color.rgb(255,255,255));

                                 String msg=task.getException().getMessage();
                                 TransitionManager.beginDelayedTransition(emailIconContainer);
                                 emailIconText.setText(msg);
                                 emailIconText.setTextColor(getResources().getColor(R.color.colorPrimary));
                                 emailIconText.setVisibility(View.VISIBLE);
//                                 Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

                             }
                                prgbar.setVisibility(View.GONE);

                            }
                        });
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new SigninFragment());
            }
        });
    }

    private void checkinputs() {
        if(TextUtils.isEmpty(registerdemail.getText().toString())&& !Patterns.EMAIL_ADDRESS
                .matcher(registerdemail.getText().toString()).matches()){
            registerdemail.setEnabled(false);
            registerdemail.setTextColor(Color.argb(50,255,255,255));
        }else {
            registerdemail.setEnabled(true);
            registerdemail.setTextColor(Color.rgb(255,255,255));

        }
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
