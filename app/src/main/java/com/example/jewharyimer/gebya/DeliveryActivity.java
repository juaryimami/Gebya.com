package com.example.jewharyimer.gebya;

import android.Manifest;
import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yenepaySDK.PaymentOrderManager;
import com.yenepaySDK.PaymentResponse;
import com.yenepaySDK.YenePayPaymentActivity;
import com.yenepaySDK.model.OrderedItem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends YenePayPaymentActivity {//YenePayPaymentActivity

    public static List<CartItemModel> cartItemModelList;
    private RecyclerView deliveryRecyclerView;
    private Button ChangeORaddNewAddress;
    public static final int SELECTED_ADDRESS=0;
    private TextView totalAmount;
    private TextView fullname;
    private String name,mobile_no;
    private TextView fullAddress;
    private TextView pincode;
    private Button continousbutton;
    private Dialog loadingDialogue;
    private Dialog paymentDialogue;
    private ImageButton payment,cod;
    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView order_ID;
    String order_id;
    private boolean successResponce=false;
    public static boolean fromCart;

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
        payment=paymentDialogue.findViewById(R.id.payment);
        cod=paymentDialogue.findViewById(R.id.code_button);

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

            }
        });
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otpIntent=new Intent(DeliveryActivity.this,OTPverificationActivity.class);
                otpIntent.putExtra("mobileNo",mobile_no.substring(0,10));
                startActivity(otpIntent);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialogue.dismiss();
                loadingDialogue.show();
                String M_id="2084";
                String customet_id=FirebaseAuth.getInstance().getUid();
                order_id=UUID.randomUUID().toString().substring(0,28);
                Double price=Double.parseDouble(totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2));
                String item_id=FirebaseAuth.getInstance().getUid();

                checkout(M_id,order_id,customet_id,"shope",price);

            }
        });

    }

    ///////////// payment methods
    private void checkout(String Mid,String order_id,String item_ID,String item_Name,Double price){

        PaymentOrderManager paymentMgr = new PaymentOrderManager(
                Mid,
                order_id);
        paymentMgr.setPaymentProcess(PaymentOrderManager.PROCESS_CART);
        paymentMgr.setReturnUrl("com.Gebya.yenepay:/payment2redirect");
        paymentMgr.setUseSandboxEnabled(true);
        paymentMgr.addItem(new OrderedItem(item_ID, item_Name,1, price));
        paymentMgr.startCheckout(this);
    }
    @Override
    public void onPaymentResponseArrived(PaymentResponse response) {
        //Handle Payment response
        if(response.isPaymentCompleted()){//Complete delivery
            successResponce=true;
            loadingDialogue.show();

            Toast.makeText(DeliveryActivity.this,"Payment successfully transfered",Toast.LENGTH_SHORT).show();
            if(MainActivity.mainActivity !=null){
                MainActivity.mainActivity.finish();
                MainActivity.mainActivity=null;
            }
            if(ProductDetailActivity.productDetailActivity !=null){
                ProductDetailActivity.productDetailActivity.finish();
                ProductDetailActivity.productDetailActivity=null;
                MainActivity.showCart=false;
            }

            if(fromCart)
            {
                long cartlist_size=0;
                final List<Integer> indexArray=new ArrayList<>();
                Map<String,Object> updateCartList=new HashMap<>();
                for(int x=0;x<DBqueries.cartlist.size();x++){
                    if(!cartItemModelList.get(x).isIn_Stock()){
                        updateCartList.put("product_ID_"+cartlist_size,cartItemModelList.get(x).getProductID());
                        cartlist_size++;
                    }else {
                        indexArray.add(x);
                    }
                }
                updateCartList.put("list_size",cartlist_size);
                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                        .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){

                         for(int x=0;x<indexArray.size();x++){
                             DBqueries.cartlist.remove(indexArray.get(x).intValue());
                             DBqueries.cartItemModelList.remove(indexArray.get(x).intValue());
                             DBqueries.cartItemModelList.remove(cartItemModelList.size()-1);
                         }

                     }else {
                         String error=task.getException().getMessage();
                         Toast.makeText(DeliveryActivity.this,error,Toast.LENGTH_SHORT).show();

                     }
                     loadingDialogue.dismiss();
                    }
                });
            }

            continousbutton.setEnabled(false);
            ChangeORaddNewAddress.setEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            orderConfirmationLayout.setVisibility(View.VISIBLE);
            order_ID.setText("Order ID"+response.getPaymentOrderId());
            continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 finish();
                }
            });
        }else {
            orderConfirmationLayout.setVisibility(View.GONE);

        }
    }
    @Override
    public void onPaymentResponseError(String error) {
        loadingDialogue.dismiss();
        Toast.makeText(DeliveryActivity.this,error,Toast.LENGTH_SHORT).show();
        //Handle payment request error.
        //showMessage(error);
    }

    ///////////////// payment methids completex here

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if(id==android.R.id.home){
           finish();
           return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        name=DBqueries.addressModelList.get(DBqueries.selectedAddress).getFullname();
        mobile_no=DBqueries.addressModelList.get(DBqueries.selectedAddress).getMobile_no();
        fullname.setText(name+" - "+mobile_no);
        fullAddress.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getPincode());

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if(successResponce)
        {
            finish();
            return;
        }
        super.onBackPressed();
    }
}
