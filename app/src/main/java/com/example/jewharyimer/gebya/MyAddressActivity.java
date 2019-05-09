package com.example.jewharyimer.gebya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.jewharyimer.gebya.DeliveryActivity.SELECTED_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private RecyclerView AddressRecyclerview;
    private static AddressAdapter addressAdapter;
    private Button deliverherebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddressRecyclerview=findViewById(R.id.address_recyclerView);
        deliverherebtn=findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        AddressRecyclerview.setLayoutManager(layoutManager);
        List<AddressModel> addressModelList=new ArrayList<>();
        addressModelList.add(new AddressModel("Jewhar yimer","Dessiew","23456745",true));
        addressModelList.add(new AddressModel("teme tmtmt","Dessiew","23456745",false));
        addressModelList.add(new AddressModel("meron bbbbb","Dessiew","23456745",true));

        int mode=getIntent().getIntExtra("MODE",-1);
        if(mode==SELECTED_ADDRESS){
            deliverherebtn.setVisibility(View.VISIBLE);
        }else {
            deliverherebtn.setVisibility(View.GONE);
        }


         addressAdapter=new AddressAdapter(addressModelList,mode);
        AddressRecyclerview.setAdapter(addressAdapter);
        ((SimpleItemAnimator)AddressRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();

    }
    public static void RefreshItem(int deselect,int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
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
