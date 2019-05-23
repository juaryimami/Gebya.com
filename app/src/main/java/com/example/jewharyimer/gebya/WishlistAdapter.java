package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private boolean wishlist;
    private int lasrPosition=-1;
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
        String productId=wishlistModelList.get(position).getProductID();
        String res=wishlistModelList.get(position).getProductImage();
        String title=wishlistModelList.get(position).getProductTitle();
        long coupeno=wishlistModelList.get(position).getFreeCoupen();
        String ratting=wishlistModelList.get(position).getRating();
        long totalratting=wishlistModelList.get(position).getTotalRating();
        String productprices=wishlistModelList.get(position).getProductPrice();
        String cutedprices=wishlistModelList.get(position).getCuttedPrice();
        boolean paymentmt=wishlistModelList.get(position).isCOD();
        boolean inStock=wishlistModelList.get(position).isInStock();
      viewHolder.setData(productId,res,title,coupeno,ratting,totalratting,productprices,cutedprices,paymentmt,position,inStock);
        if(lasrPosition<position){
            Animation animation=AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lasrPosition=position;
        }
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
        private void setData(final String productId, String resource, String title, long freeCoupenNo, String averageRate
                , long totalratingNo, String price, String cuttedpriceValue, boolean payMethod, final int index,boolean inStock){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.place_holder)).into(productImage);
            productTitle.setText(title);
            if(freeCoupenNo!=0&& inStock){
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
            LinearLayout linearLayout= (LinearLayout) rating.getParent();
            if(inStock)
            {
                rating.setVisibility(View.VISIBLE);
                totalrating.setVisibility(View.VISIBLE);
                productprice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);

                rating.setText(averageRate);
                totalrating.setText("("+totalratingNo+") Ratings");
                productprice.setText("Br."+price+"/-");
                cuttedPrice.setText("Br."+cuttedpriceValue+"/-");
                if(payMethod){
                    paymentMethod.setVisibility(View.VISIBLE);
                }else {
                    paymentMethod.setVisibility(View.INVISIBLE);
                }
            }else {
                linearLayout.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalrating.setVisibility(View.INVISIBLE);
                productprice.setText("Out of Stock");
                productprice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setVisibility(View.INVISIBLE);
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
                    if(ProductDetailActivity.running_wishlist_query){
                    ProductDetailActivity.running_wishlist_query=true;
                    DBqueries.removeFromWishlist(index, itemView.getContext());}
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ProductIntent=new Intent(itemView.getContext(),ProductDetailActivity.class);
                    ProductIntent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(ProductIntent);
                }
            });
        }
    }
}
