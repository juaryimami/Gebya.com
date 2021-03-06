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

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModels;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder viewHolder, int i) {
           String resource=horizontalProductScrollModels.get(i).getProductImage();
           String title=horizontalProductScrollModels.get(i).getProductTitle();
           String description=horizontalProductScrollModels.get(i).getProductDescription();
           String price=horizontalProductScrollModels.get(i).getProductPrice();
           String productid=horizontalProductScrollModels.get(i).getProductID();

           viewHolder.setData(productid,resource,title,description,price);

    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollModels.size() > 8) {
            return 8;

        }else {
            return horizontalProductScrollModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ProductImage;
        private TextView ProductTitle;
        private TextView ProductDescription;
        private TextView ProductPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.h_s_product_image);
            ProductTitle=itemView.findViewById(R.id.h_s_product_text);
            ProductDescription=itemView.findViewById(R.id.h_s_product_desctiption);
            ProductPrice=itemView.findViewById(R.id.h_s_product_price);


        }

        private void setData(final String product_id, String resource, String title, String description, String price){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.place_holder)).into(ProductImage);
            ProductPrice.setText("Br."+price+"/-");
            ProductTitle.setText(title);
            ProductDescription.setText(description);
           // ProductImage.setImageResource(resource);

            if(!title.equals("")){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent=new Intent(itemView.getContext(),ProductDetailActivity.class);
                    productDetailIntent.putExtra("PRODUCT_ID",product_id);
                    itemView.getContext().startActivity(productDetailIntent);

                }
            });
            }
        }


    }
}
