package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {

    private List<Category_Model> category_modelList;

    public Category_Adapter(List<Category_Model> category_modelList) {
        this.category_modelList = category_modelList;
    }

    @NonNull
    @Override
    public Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.ViewHolder viewHolder, int position) {
     String icon=category_modelList.get(position).getCategoryIconLink();
     String name=category_modelList.get(position).getCategoryName();
     viewHolder.setCategory(name,position  );
     viewHolder.setCatagoryIconlink(icon);
    }

    @Override
    public int getItemCount() {
        return category_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView catagoryIconlink;
        private TextView categoryName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catagoryIconlink=itemView.findViewById(R.id.category_icon1_view);
            categoryName=itemView.findViewById(R.id.category_name);
        }
        private void setCatagoryIconlink(String iconUrl){
            if(iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.home)).into(catagoryIconlink);
            }
        }
        private void setCategory(final String name,final int position){
            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0){
                        Intent catagoryIntent=new Intent(itemView.getContext(),CategoryActivity.class);
                        catagoryIntent.putExtra("categoryName",name);
                        itemView.getContext().startActivity(catagoryIntent);

                    }
                    }
            });
        }
    }
}
