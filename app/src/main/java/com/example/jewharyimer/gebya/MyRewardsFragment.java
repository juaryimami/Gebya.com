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
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView myRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_rewards, container, false);
        myRecyclerView=view.findViewById(R.id.my_rewardRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList=new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));

        MyRewardsadapter rewardsadapter=new MyRewardsadapter(rewardModelList);
        myRecyclerView.setAdapter(rewardsadapter);
        rewardsadapter.notifyDataSetChanged();
        return  view;
    }

}
