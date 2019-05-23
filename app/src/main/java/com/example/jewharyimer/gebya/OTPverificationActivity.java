package com.example.jewharyimer.gebya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class OTPverificationActivity extends AppCompatActivity {
    private TextView phone_no;
    private EditText otp;
    private Button verify;
    private String userNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        phone_no=findViewById(R.id.phone_no);
        otp=findViewById(R.id.otp);
        verify=findViewById(R.id.verifyOTP);
        userNo=getIntent().getStringExtra("mobileNo");
        phone_no.setText("Verification code has been sent to"+userNo);

        Random random = new Random();
        int OTP_numbers=random.nextInt(999999-111111) + 111111;

        //           335838  StringRequest
    }
}
