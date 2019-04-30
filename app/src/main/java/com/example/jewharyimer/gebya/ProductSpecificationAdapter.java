package com.example.jewharyimer.gebya;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.viewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModelList.get(position).getType()){
            case 0:
               return ProductSpecificationModel.SPECIFICATION_TITLE;
            case 1:
              return ProductSpecificationModel.SPECIFICATION_BODY;
                default:
                    return -1;
        }
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                TextView title=new TextView(viewGroup.getContext());
                title.setTypeface(null,Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDP(16,viewGroup.getContext()),setDP(16,viewGroup.getContext()),
                        setDP(16,viewGroup.getContext()),setDP(8,viewGroup.getContext()));
                title.setLayoutParams(layoutParams);
                return new viewHolder(title);
                case ProductSpecificationModel.SPECIFICATION_BODY:
                    View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_specification_item_layout,viewGroup,false);
                    return new viewHolder(view);

            default:
                return null;
        }
           }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.viewHolder viewHolder, int position) {
        switch (productSpecificationModelList.get(position).getType()){
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                 viewHolder.setTitle(productSpecificationModelList.get(position).getTitle());
                break;
                case ProductSpecificationModel.SPECIFICATION_BODY:
                    String featureTitle=productSpecificationModelList.get(position).getFeatureName();
                    String featureDetails=productSpecificationModelList.get(position).getFeaturValue();
                    viewHolder.setFeatures(featureTitle,featureDetails);
                    break;
            default:
                        return;
        }

    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView featureName;
        private TextView featureValue;
        private TextView title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

        }
        private void setTitle(String textTitle){
            title= (TextView) itemView;
            title.setText(textTitle);
        }
        private void setFeatures(String featureTitle,String featureDetails){
            featureName=itemView.findViewById(R.id.feature_name);
            featureValue=itemView.findViewById(R.id.feature_value);
            featureName.setText(featureTitle);
            featureValue.setText(featureDetails);
        }
    }
    private int setDP(int dp,Context context){

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
