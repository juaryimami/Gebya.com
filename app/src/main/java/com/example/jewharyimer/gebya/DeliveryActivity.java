package com.example.jewharyimer.gebya;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {

    public static List<CartItemModel> cartItemModelList;
    private RecyclerView deliveryRecyclerView;
    private Button ChangeORaddNewAddress;
    public static final int SELECTED_ADDRESS=0;
    private TextView totalAmount;
    private TextView fullname;
    private TextView fullAddress;
    private TextView pincode;
    private Button continousbutton;
    private Dialog loadingDialogue;
    private Dialog paymentDialogue;
    private ImageButton payment;
    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView order_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        deliveryRecyclerView=findViewById(R.id.delivery_recycler_view);
        ChangeORaddNewAddress=findViewById(R.id.change_add_address_btn);
        totalAmount=findViewById(R.id.total_cart_amount);
        fullname=findViewById(R.id.full_name);
        fullAddress=findViewById(R.id.adderess);
        pincode=findViewById(R.id.pincode);
        continousbutton=findViewById(R.id.cart_continue_btn);
        orderConfirmationLayout=findViewById(R.id.order_confirmation_layout);
        order_ID=findViewById(R.id.order_id);
        continueShoppingBtn=findViewById(R.id.continue_shopping_btnn);

        ////////////// dialogue
        loadingDialogue=new Dialog(this);
        loadingDialogue.setContentView(R.layout.loading_progress_dialogue);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);

        ///////////// dialogue
        ////////////// dialogue
        paymentDialogue=new Dialog(this);
        paymentDialogue.setContentView(R.layout.payment_method);
        paymentDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paymentDialogue.setCancelable(true);

        ///////////// dialogue



        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList,totalAmount,false);
        cartAdapter.notifyDataSetChanged();
        ChangeORaddNewAddress.setVisibility(View.VISIBLE);
        ChangeORaddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressintent=new Intent(DeliveryActivity.this,AddAddressActivity.class);
                myaddressintent.putExtra("MODE",SELECTED_ADDRESS);
                startActivity(myaddressintent);
            }
        });

        continousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialogue.show();
                payment=paymentDialogue.findViewById(R.id.payment);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialogue.dismiss();
                loadingDialogue.show();
                if(ContextCompat.checkSelfPermission(DeliveryActivity.this,Manifest.permission.READ_SMS)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DeliveryActivity.this,new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},101);
                }

                String M_id="kngskfkjslj993920";// should be on from the website
                String customet_id=FirebaseAuth.getInstance().getUid();
                String order_id=UUID.randomUUID().toString().substring(0,28);
                String url="https://gebya_dot_com.000webhostapp.com/paytm/generateChecksum.php";
                String callBackUrl ="https://pguat.paytm.com/paytmchecksum/paytmcallback.jsp";

                //StrinRequest strinRequest=new StringRequest(Request.Method.POST,url,new Response.L);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if(id==android.R.id.home){
           finish();
           return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        fullname.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getFullname());
        fullAddress.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getPincode());

        super.onStart();
    }
}
