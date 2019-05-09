package com.example.jewharyimer.gebya;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.jewharyimer.gebya.ProductDetailActivity.productDescription;
import static com.example.jewharyimer.gebya.ProductDetailActivity.productOtherDetails;
import static com.example.jewharyimer.gebya.ProductDetailActivity.tabPosition;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {


    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView discriptionBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_description, container, false);
        discriptionBody=view.findViewById(R.id.tv_product_description);
        if(tabPosition==0) {
            discriptionBody.setText(productDescription);
        }else {
            discriptionBody.setText(productOtherDetails);
        }
        return view; }

}
