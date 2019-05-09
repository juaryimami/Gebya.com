package com.example.jewharyimer.gebya;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jewharyimer.gebya.Category_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.jewharyimer.gebya.DBqueries.category_modelList;
import static com.example.jewharyimer.gebya.DBqueries.firebaseFirestore;
import static com.example.jewharyimer.gebya.DBqueries.lists;
import static com.example.jewharyimer.gebya.DBqueries.lists;
import static com.example.jewharyimer.gebya.DBqueries.loadFragmentData;
import static com.example.jewharyimer.gebya.DBqueries.loadcategories;
import static com.example.jewharyimer.gebya.DBqueries.loadedCategoriesName;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private Category_Adapter category_adapter;
    RecyclerView homePageRecyclerView;

    private ImageView no_internate_connection;
    private HomePageAdapter homePageAdapter;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_, container, false);
        no_internate_connection=view.findViewById(R.id.no_internete_connection);


        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()==true) {
            no_internate_connection.setVisibility(View.GONE);

            categoryRecyclerView=view.findViewById(R.id.catagiry_recyclerview);

            LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);


            category_adapter =new Category_Adapter(category_modelList);
            categoryRecyclerView.setAdapter(category_adapter);

            ///////////////////////////////////
            homePageRecyclerView=view.findViewById(R.id.testing_recyclerView);
            LinearLayoutManager testingLineatLayout=new LinearLayoutManager(getContext());
            testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(testingLineatLayout);


            if(lists.size()==0){
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                homePageAdapter=new HomePageAdapter(lists.get(0));
                loadFragmentData(homePageAdapter,getContext(),0,"HOME");
            }else {
                homePageAdapter=new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);


        }else {
            Glide.with(this).load(R.drawable.no_connection).into(no_internate_connection);
            no_internate_connection.setVisibility(View.VISIBLE);
        }





        return view;
    }
}





