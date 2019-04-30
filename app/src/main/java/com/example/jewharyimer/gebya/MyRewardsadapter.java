package com.example.jewharyimer.gebya;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRewardsadapter extends RecyclerView.Adapter<MyRewardsadapter.ViewHolder> {

    public List<RewardModel> rewardModelList;

    public MyRewardsadapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    @NonNull
    @Override
    public MyRewardsadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRewardsadapter.ViewHolder viewHolder, int position) {
        String title=rewardModelList.get(position).getTitle();
        String exp=rewardModelList.get(position).getExpirdate();
        String body=rewardModelList.get(position).getCoupenbody();
        viewHolder.setData(title,exp,body);

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView coupenTitle;
        private TextView expiredate;
        private TextView coupenBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupenTitle=itemView.findViewById(R.id.coupen_title);
            expiredate=itemView.findViewById(R.id.coupen_validity);
            coupenBody=itemView.findViewById(R.id.coupen_body);
        }
        private void setData(String title, String expire, String body){
            coupenTitle.setText(title);
            expiredate.setText(expire);
            coupenBody.setText(body);
        }
    }
}
