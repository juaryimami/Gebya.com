package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button ChangeORaddNewAddress;
    public static final int SELECTED_ADDRESS=0;
    private TextView totalAmount;

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

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);
        List<CartItemModel> cartItemModelList=new ArrayList<>();
       // cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Br. 499999","Free","Br. 1699999","Br.4999999"));

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList,totalAmount);
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

}
