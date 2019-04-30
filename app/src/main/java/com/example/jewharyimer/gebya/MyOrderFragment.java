package com.example.jewharyimer.gebya;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {


    public MyOrderFragment() {
        // Required empty public constructor
    }
 private RecyclerView myOrderRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_order, container, false);
    myOrderRecyclerView=view.findViewById(R.id.my_orders_RecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.im4,2,"Habesha Kemis II","Delivered on Mon,15th Jan 2019"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.im4,2,"Habesha Kemis II","Delivered on Mon,15th Jan 2019"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.im4,2,"Habesha Kemis II","cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.im4,2,"Habesha Kemis II","Delivered on Mon,15th Jan 2019"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.im4,2,"Habesha Kemis II","Delivered on Mon,15th Jan 2019"));

        MyOderAdapter myOderAdapter=new MyOderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOderAdapter);
        myOderAdapter.notifyDataSetChanged();
        return view;
    }

}
