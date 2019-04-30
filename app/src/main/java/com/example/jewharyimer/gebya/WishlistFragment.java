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
public class WishlistFragment extends Fragment {


    public WishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wislistRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_wishlist, container, false);
    wislistRecyclerView=view.findViewById(R.id.my_wishlistRecyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wislistRecyclerView.setLayoutManager(linearLayoutManager);
        List<WishlistModel> wishlistModelList=new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",0,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",1,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",2,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",3,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",4,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.im4,"Habesha kemis now",5,"3",145,"Br. 49999/-","Br. 59999 /-","cash on delivery"));

        WishlistAdapter wishlistAdapter=new WishlistAdapter(wishlistModelList);
        wislistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;}

}
