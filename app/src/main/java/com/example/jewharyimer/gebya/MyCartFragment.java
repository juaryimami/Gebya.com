package com.example.jewharyimer.gebya;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecyclerView=view.findViewById(R.id.cart_item_recyclerView);
        continueButton=view.findViewById(R.id.cart_continue_btn);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayoutManager);
        List<CartItemModel> cartItemModelList=new ArrayList<>();

        cartItemModelList.add(new CartItemModel(0,R.drawable.im3,"Raya kemis new"
                ,2,"Br. 49999/-","Br. 49999/-"
                ,1,0,1));
        cartItemModelList.add(new CartItemModel(0,R.drawable.im3,"Raya kemis new"
                ,0,"Br. 49999/-","Br. 49999/-"
                ,1,1,1));
        cartItemModelList.add(new CartItemModel(0,R.drawable.im3,"Raya kemis new"
                ,2,"Br. 49999/-","Br. 49999/-"
                ,1,2,1));


        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Br. 499999","Free","Br. 1699999","Br.4999999"));

                CartAdapter cartAdapter=new CartAdapter(cartItemModelList);
                cartAdapter.notifyDataSetChanged();

                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(),AddAddressActivity.class);
                        getContext().startActivity(intent);
                    }
                });


        return view;
    }

}
