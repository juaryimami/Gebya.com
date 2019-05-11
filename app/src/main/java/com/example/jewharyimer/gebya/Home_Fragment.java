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
import android.support.v4.widget.SwipeRefreshLayout;
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
    public static SwipeRefreshLayout swipeRefreshLayout;
    private Category_Adapter category_adapter;
    private List<Category_Model> category_modelFakeList=new ArrayList<>();
    private List<HomePageModel> homeFakePageModelList=new ArrayList<>();
    RecyclerView homePageRecyclerView;

    private ImageView no_internate_connection;
    private HomePageAdapter homePageAdapter;
    private Button retryBtn;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_home_, container, false);
        no_internate_connection=view.findViewById(R.id.no_internete_connection);
        swipeRefreshLayout=view.findViewById(R.id.refresh_layout);
        retryBtn=view.findViewById(R.id.retry_button);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));


        categoryRecyclerView=view.findViewById(R.id.catagiry_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);


        /////////////   category fake list
        category_modelFakeList.add(new Category_Model("null",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));
        category_modelFakeList.add(new Category_Model("",""));



        //////////////// category fake list




        categoryRecyclerView=view.findViewById(R.id.categoryRecycview);


        ///////////////////////////////////
        //cattagoryRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout=new LinearLayoutManager(getContext());
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLineatLayout);

        /////////////// home page fake list
        List<SliderModel> slideFakerModelList=new ArrayList<>();
        slideFakerModelList.add(new SliderModel("null","#dfdfdf"));
        slideFakerModelList.add(new SliderModel("null","#dfdfdf"));
        slideFakerModelList.add(new SliderModel("null","#dfdfdf"));
        slideFakerModelList.add(new SliderModel("null","#dfdfdf"));
        slideFakerModelList.add(new SliderModel("null","#dfdfdf"));

        List<HorizontalProductScrollModel> horizontalProductScrollModeFakelList=new ArrayList<>();
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));

        homeFakePageModelList.add(new HomePageModel(0,slideFakerModelList));
        homeFakePageModelList.add(new HomePageModel(1,"","#dfdfdf"));
        homeFakePageModelList.add(new HomePageModel(2,"","#dfdfdf",horizontalProductScrollModeFakelList,new ArrayList<WishlistModel>()));
        homeFakePageModelList.add(new HomePageModel(3,"","#dfdfdf",horizontalProductScrollModeFakelList));
        /////////////// home page fake list


        category_adapter =new Category_Adapter(category_modelFakeList);

        homePageAdapter=new HomePageAdapter(homeFakePageModelList);
        homePageRecyclerView.setAdapter(homePageAdapter);

        homePageRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout1=new LinearLayoutManager(getContext());
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLineatLayout1);


        connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkInfo!=null && networkInfo.isConnected()==true) {
            no_internate_connection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            category_adapter=new Category_Adapter(category_modelFakeList);
            homePageAdapter=new HomePageAdapter(homeFakePageModelList);
            categoryRecyclerView.setAdapter(category_adapter);

            if(category_modelList.size()==0){
            loadcategories(categoryRecyclerView,getContext());
            }else {
                category_adapter=new Category_Adapter(category_modelList);
                category_adapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(category_adapter);
            ///////////////////////////////////

            if(lists.size()==0){
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");
            }else {
                homePageAdapter=new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);

        }else {
            categoryRecyclerView.setVisibility(View.GONE);
            retryBtn.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_connection).into(no_internate_connection);
            no_internate_connection.setVisibility(View.VISIBLE);
        }


           //////// refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                relodPage();


                }
        });
           //////// refresh layout
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relodPage();
            }
        });

        return view;
    }
    private  void relodPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        category_modelList.clear();
        lists.clear();
        loadedCategoriesName.clear();

        if (networkInfo!=null && networkInfo.isConnected()==true) {
            no_internate_connection.setVisibility(View.GONE);
            loadcategories(categoryRecyclerView,getContext());
            loadedCategoriesName.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");

        }else {
            Toast.makeText(getContext(),"no interner connection",Toast.LENGTH_SHORT).show();
            Glide.with(getContext()).load(R.drawable.no_connection).into(no_internate_connection);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            no_internate_connection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}





