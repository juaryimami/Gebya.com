package com.example.jewharyimer.gebya;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {

    private RecyclerView cartItemRecyclerView;
    private Button continueButton;

    public MyCartFragment() {
        // Required empty public constructor
    }
    private RecyclerView wislistRecyclerView;
    private Dialog loadingDialogue;
    public static CartAdapter cartAdapter;
    private TextView totalAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        totalAmount=view.findViewById(R.id.total_cart_amount);
        ////////////// dialogue
        loadingDialogue=new Dialog(getContext());
        loadingDialogue.setContentView(R.layout.loading_progress_dialogue);
        loadingDialogue.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);
        loadingDialogue.show();

        ///////////// dialogue

        if(DBqueries.cartlist.size()==0){
            DBqueries.cartlist.clear();
            DBqueries.loadCartList(getContext(),loadingDialogue,true,new TextView(getContext()));

        }else{
            loadingDialogue.dismiss();
        }

        cartItemRecyclerView=view.findViewById(R.id.cart_item_recyclerView);
        continueButton=view.findViewById(R.id.cart_continue_btn);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList=new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0,R.drawable.im3,"Raya kemis new",2,"Br. 49999/-","Br. 49999/-",1,0,1));

        CartAdapter cartAdapter=new CartAdapter(DBqueries.cartItemModelList,totalAmount);
        cartAdapter.notifyDataSetChanged();

        continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialogue.show();
                        DBqueries.loadAddress(getContext(),loadingDialogue);
                    }
                });


        return view;
    }

}
