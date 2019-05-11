package com.example.jewharyimer.gebya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String OtheerDescription;

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(FragmentManager fm, int totalTabs ,String productDescription, String otheerDescription, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDescription = productDescription;
        OtheerDescription = otheerDescription;
        this.productSpecificationModelList = productSpecificationModelList;
        this.totalTabs=totalTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
            ProductDescriptionFragment productDescriptionFragment1=new ProductDescriptionFragment();
                productDescriptionFragment1.body=productDescription;
            return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment=new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList=productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productSpecificationFragment1=new ProductDescriptionFragment();
                productSpecificationFragment1.body=OtheerDescription;
                return productSpecificationFragment1;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
