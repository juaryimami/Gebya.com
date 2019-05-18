package com.example.jewharyimer.gebya;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private EditText city;
    private EditText localcity;
    private EditText flatNo;
    private EditText pincode;
    private EditText landMark;
    private EditText name;
    private EditText mobileNo;
    private EditText alternateMobileNo;
    private Spinner stateSpinner;

    private String [] statlist;

    private Button saveBtn;
    private String selectedState;
    private Dialog loadingDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("ADD A NEW ADDRESS");

        statlist=getResources().getStringArray(R.array.Ethiopian_city);

        ////////////// dialogue
        loadingDialogue=new Dialog(AddAddressActivity.this);
        loadingDialogue.setContentView(R.layout.loading_progress_dialogue);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);

        ///////////// dialogue

        city=findViewById(R.id.city);
        localcity=findViewById(R.id.locality_area_sttreet);
        flatNo=findViewById(R.id.building_name);
        pincode=findViewById(R.id.pincode_no);
        name=findViewById(R.id.name);
        mobileNo=findViewById(R.id.mobile_no);
        alternateMobileNo=findViewById(R.id.alternate_mobile_no);
        landMark=findViewById(R.id.landmark);
        stateSpinner=findViewById(R.id.state_spinner);
        saveBtn=findViewById(R.id.save_btn);

        ArrayAdapter spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,statlist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState=statlist[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(city.getText())){
                    if(!TextUtils.isEmpty(localcity.getText())){

                        if(!TextUtils.isEmpty(flatNo.getText())){

                            if(!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length()==6){

                                if(!TextUtils.isEmpty(name.getText())){

                                    if(!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length()<10){

                                        loadingDialogue.show();
                                        final String fullAddress=flatNo.getText().toString()+" "+localcity.getText().toString()+" "+landMark.getText().toString()+" "+city.getText().toString()+" "+selectedState;

                                        Map<String,Object> addresmap=new HashMap<>();
                                        addresmap.put("list_size",(long)DBqueries.addressModelList.size()+1);
                                        if(TextUtils.isEmpty(alternateMobileNo.getText())) {
                                            addresmap.put("fullname_" + String.valueOf((long) DBqueries.addressModelList.size() + 1), name.getText().toString() + " - " + mobileNo.getText().toString());
                                        }else {
                                            addresmap.put("fullname_" + String.valueOf((long) DBqueries.addressModelList.size() + 1), name.getText().toString() + " - " + mobileNo.getText().toString()+" or "+alternateMobileNo.getText().toString());
                                        }
                                        addresmap.put("address_"+String.valueOf((long)DBqueries.addressModelList.size()+1),fullAddress);
                                        addresmap.put("pincode_"+String.valueOf((long)DBqueries.addressModelList.size()+1),pincode.getText().toString());
                                        addresmap.put("selected_"+String.valueOf((long)DBqueries.addressModelList.size()+1),true);

                                        if(DBqueries.addressModelList.size()>0){
                                            addresmap.put("selected_"+(DBqueries.selectedAddress+1),false);
                                        }
                                        FirebaseFirestore.getInstance().collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESSES")
                                                .update(addresmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){
                                                    if(DBqueries.addressModelList.size()>0){
                                                        DBqueries.addressModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                    }
                                                    if(TextUtils.isEmpty(alternateMobileNo.getText())) {
                                                        DBqueries.addressModelList.add(new AddressModel(name.getText().toString() + " - " + mobileNo.getText().toString(), fullAddress, pincode.getText().toString(), true));
                                                    }else {
                                                        DBqueries.addressModelList.add(new AddressModel(name.getText().toString() + " - " + mobileNo.getText().toString()+" or "+alternateMobileNo.getText().toString(), fullAddress, pincode.getText().toString(), true));
                                                    }

                                                    DBqueries.selectedAddress=DBqueries.addressModelList.size()-1;
                                                    Intent intent=new Intent(AddAddressActivity.this,DeliveryActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    String error=task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this,error,Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialogue.dismiss();
                                            }
                                        });

                                    }else {
                                        mobileNo.requestFocus();
                                        Toast.makeText(AddAddressActivity.this,"please provide valid Mobile number",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    name.requestFocus();
                                }
                            }else {
                                pincode.requestFocus();
                                Toast.makeText(AddAddressActivity.this,"please provide valid pincode",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            flatNo.requestFocus();
                        }
                    }else {
                        localcity.requestFocus();
                    }
                }else {
                    city.requestFocus();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
