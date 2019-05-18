package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jewharyimer.gebya.DeliveryActivity.SELECTED_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private int priviousAddress;
    private RecyclerView AddressRecyclerview;
    private static AddressAdapter addressAdapter;
    private Button deliverherebtn;

    private LinearLayout addNewAddressBtn;
    private TextView addresSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        priviousAddress=DBqueries.selectedAddress;

        AddressRecyclerview=findViewById(R.id.address_recyclerView);
        deliverherebtn=findViewById(R.id.deliver_here_btn);
        addNewAddressBtn=findViewById(R.id.add_new_address_btn);
        addresSaved=findViewById(R.id.address_saved);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        AddressRecyclerview.setLayoutManager(layoutManager);

        int mode=getIntent().getIntExtra("MODE",-1);
        if(mode==SELECTED_ADDRESS){
            deliverherebtn.setVisibility(View.VISIBLE);
        }else {
            deliverherebtn.setVisibility(View.GONE);
        }

        deliverherebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBqueries.selectedAddress!=priviousAddress)
                {
                    final int previousAddressIndex=priviousAddress;
                    Map<String,Object> updateSelection=new HashMap<>();
                    updateSelection.put("selected_"+String.valueOf(priviousAddress+1),false);
                    updateSelection.put("selected_"+String.valueOf(DBqueries.selectedAddress+1),true);

                    priviousAddress=DBqueries.selectedAddress;
                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                            .document("MY_ADDRESS").update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                          finish();
                         }else {
                             priviousAddress=previousAddressIndex;
                             String error=task.getException().getMessage();
                             Toast.makeText(MyAddressActivity.this,error,Toast.LENGTH_SHORT).show();
                         }

                        }
                    });
                }
            }
        });

         addressAdapter=new AddressAdapter(DBqueries.addressModelList,mode);
        AddressRecyclerview.setAdapter(addressAdapter);
        ((SimpleItemAnimator)AddressRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();

        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAdressIntent=new Intent(MyAddressActivity.this,AddAddressActivity.class);
                addAdressIntent.putExtra("INTENT","null");
                startActivity(addAdressIntent);
            }
        });
        addresSaved.setText(String.valueOf(DBqueries.addressModelList.size())+" saved Addresses");

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
