package com.example.jewharyimer.gebya;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.jewharyimer.gebya.ProductDetailActivity.productSpecificationModelList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {
    private RecyclerView productSpecificationRecyclerView;



    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
         productSpecificationRecyclerView=view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerView.setLayoutManager(layoutManager);

        productSpecificationModelList=new ArrayList<>();
       /*
        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Dissplay"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Dissplay"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
*/

        ProductSpecificationAdapter productSpecificationAdapter=new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return  view;}

}
