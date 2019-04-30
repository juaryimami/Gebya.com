package com.example.jewharyimer.gebya;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int position) {
        int res=wishlistModelList.get(position).getProductImage();
        String title=wishlistModelList.get(position).getProductTitle();
        int coupeno=wishlistModelList.get(position).getFreeCoupen();
        String ratting=wishlistModelList.get(position).getRating();
        int totalratting=wishlistModelList.get(position).getTotalRating();
        String productprices=wishlistModelList.get(position).getProductPrice();
        String cutedprices=wishlistModelList.get(position).getCuttedPrice();
        String paymentmt=wishlistModelList.get(position).getPaymentMethod();
      viewHolder.setData(res,title,coupeno,ratting,totalratting,productprices,cutedprices,paymentmt);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView coupenIcon;
        private TextView productTitle;
        private TextView freeCoupen;
        private TextView rating;
        private View priceCut;
        private ImageView deleteBtn;
        private TextView totalrating;
        private TextView productprice;
        private TextView cuttedPrice;
        private TextView paymentMethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            freeCoupen=itemView.findViewById(R.id.free_coupen);
            coupenIcon=itemView.findViewById(R.id.free_coupen_icon);
            rating=itemView.findViewById(R.id.tv_product_ratting_miniView);
            totalrating=itemView.findViewById(R.id.totalRating);
            priceCut=itemView.findViewById(R.id.divider13);
            productprice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_price);
            paymentMethod=itemView.findViewById(R.id.payment_method);
            deleteBtn=itemView.findViewById(R.id.delete_btn);

        }
        private void setData(int resource, String title, int freeCoupenNo, String averageRate, int totalratingNo, String price, String cuttedpriceValue, String payMethod){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(freeCoupenNo!=0){
                if(freeCoupenNo==1){
                    coupenIcon.setVisibility(View.VISIBLE);
                    freeCoupen.setVisibility(View.VISIBLE);
                freeCoupen.setText("free "+ freeCoupenNo+" coupen");
                }else {
                    freeCoupen.setText("free "+ freeCoupenNo+" coupens");
                }
            }else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupen.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalrating.setText(totalratingNo+"  (Ratings)");
            productprice.setText(price);
            cuttedPrice.setText(cuttedpriceValue);
            paymentMethod.setText(payMethod);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"deleted",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
