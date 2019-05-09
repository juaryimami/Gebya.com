package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private boolean wishlist;
    List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList,boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist=wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int position) {
        String res=wishlistModelList.get(position).getProductImage();
        String title=wishlistModelList.get(position).getProductTitle();
        long coupeno=wishlistModelList.get(position).getFreeCoupen();
        String ratting=wishlistModelList.get(position).getRating();
        long totalratting=wishlistModelList.get(position).getTotalRating();
        String productprices=wishlistModelList.get(position).getProductPrice();
        String cutedprices=wishlistModelList.get(position).getCuttedPrice();
        boolean paymentmt=wishlistModelList.get(position).isCOD();
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
            productTitle=itemView.findViewById(R.id.tv_product_title);
            freeCoupen=itemView.findViewById(R.id.free_coupen);
            coupenIcon=itemView.findViewById(R.id.free_coupen_icon);
            rating=itemView.findViewById(R.id.tv_product_ratting_miniView);
            totalrating=itemView.findViewById(R.id.totalRating);
            priceCut=itemView.findViewById(R.id.divider13);
            productprice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.tv_cutted_price);
            paymentMethod=itemView.findViewById(R.id.payment_method);
            deleteBtn=itemView.findViewById(R.id.delete_btn);

        }
        private void setData(String resource, String title, long freeCoupenNo, String averageRate
                , long totalratingNo, String price, String cuttedpriceValue, boolean payMethod){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.home)).into(productImage);
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
            totalrating.setText("("+totalratingNo+") Ratings");
            productprice.setText("Br."+price+"/-");
            cuttedPrice.setText("Br."+cuttedpriceValue+"/-");
            if(payMethod){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }
           // paymentMethod.setText(payMethod);

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"deleted",Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ProductIntent=new Intent(itemView.getContext(),ProductDetailActivity.class);
                    itemView.getContext().startActivity(ProductIntent);
                }
            });
        }
    }
}
