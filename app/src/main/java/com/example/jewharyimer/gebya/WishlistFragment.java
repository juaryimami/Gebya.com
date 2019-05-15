package com.example.jewharyimer.gebya;


import android.app.Dialog;
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
    private Dialog loadingDialogue;
    public static WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_wishlist, container, false);
       ////////////// dialogue
        loadingDialogue=new Dialog(getContext());
        loadingDialogue.setContentView(R.layout.loading_progress_dialogue);
        loadingDialogue.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);
        loadingDialogue.show();

        ///////////// dialogue
    wislistRecyclerView=view.findViewById(R.id.my_wishlistRecyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wislistRecyclerView.setLayoutManager(linearLayoutManager);
//        List<WishlistModel> wishlistModelList=new ArrayList<>();
        if(DBqueries.wishhlist.size()==0){
            DBqueries.wishhlist.clear();
            DBqueries.loadWishlist(getContext(),loadingDialogue,true);

        }else{
            loadingDialogue.dismiss();
        }

         wishlistAdapter=new WishlistAdapter(DBqueries.wishlistModelList,false);
        wislistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;}

}
