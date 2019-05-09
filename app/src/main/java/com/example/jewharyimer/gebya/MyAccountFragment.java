package com.example.jewharyimer.gebya;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static final int MANAGE_ADDRESS=1;
    private Button viewAllAddressbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAllAddressbutton=view.findViewById(R.id.view_all_btnAddress);

        viewAllAddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressintent=new Intent(getContext(),AddAddressActivity.class);
                myaddressintent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(myaddressintent);
            }
        });
        return view;
    }

}
