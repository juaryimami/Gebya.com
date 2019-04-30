package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyOderAdapter extends RecyclerView.Adapter<MyOderAdapter.Viewholder> {
    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_orders_item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOderAdapter.Viewholder viewHolder, int position) {
        int resource=myOrderItemModelList.get(position).getProductImage();
        int rattint=myOrderItemModelList.get(position).getRatting();
        String title=myOrderItemModelList.get(position).getProductTitle();
        String deliveryDate=myOrderItemModelList.get(position).getDeliveryStatus();
        viewHolder.setData(resource,title,deliveryDate,rattint);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTotle;
        private TextView orderStatus;
        private LinearLayout rateNowContainer;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_order);
            orderIndicator=itemView.findViewById(R.id.order_indicator);
            productTotle=itemView.findViewById(R.id.product_title_of_order);
            orderStatus=itemView.findViewById(R.id.order_delivery_date);
            rateNowContainer=itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderIntent=new Intent(itemView.getContext(),OrderDetailActivity.class);
                    itemView.getContext().startActivity(orderIntent);
                }
            });
        }
        private void setData(int resource,String title,String deliveruyStatus,int ratting){
            productImage.setImageResource(resource);
            productTotle.setText(title);
           // orderStatus.setText(deliveruyStatus);
           // orderIndicator.setImageResource(indicator);
            if (deliveruyStatus.equals("cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));

            }else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));
            }
            orderStatus.setText(deliveruyStatus);

            /////// rating selected

            setRating(ratting);

            for(int x=0;x<rateNowContainer.getChildCount();x++){
                final int starPosition=x;
//            final int X = x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);

                    }
                });
            }
            /////// rating layout

            }
        public void setRating(int starPosition){
            for(int x=0;x<rateNowContainer.getChildCount();x++){
                ImageView starButn=(ImageView)rateNowContainer.getChildAt(x);
                starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x<=starPosition){
                    starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("ffbb00")));
                }
            }
        }
        }
    }

