package com.example.jewharyimer.gebya;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {


    private List<CartItemModel> cartItemModelList;
    private int lasrPosition=-1;
    private TextView cartTotalAmount;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount=cartTotalAmount;
    }

    @Override
    public int getItemViewType(int position) {
       switch (cartItemModelList.get(position).getTYPE()){
           case 0:
               return CartItemModel.CART_ITEM;
               case 1:
                   return CartItemModel.TOTAL_AMOUNT;
                   default:
                       return -1;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        switch (position){
            case CartItemModel.CART_ITEM:
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                return new CartItemViewHolder(view);
                case CartItemModel.TOTAL_AMOUNT:
                    View view1=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout,viewGroup,false);
                    return new CartTotalAmountViewHolder(view1);
                    default:
                        return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getTYPE()){
            case CartItemModel.CART_ITEM:
                String productID=cartItemModelList.get(position).getProductID();
                String resource=cartItemModelList.get(position).getProuductImage();
                String title=cartItemModelList.get(position).getProductTitle();
                long freecoupens=cartItemModelList.get(position).getFreeCoupens();
                String productPrice=cartItemModelList.get(position).getProductPrice();
                String cuttedPrice=cartItemModelList.get(position).getCuttedPrice();
                long offerApplied=cartItemModelList.get(position).getOffersApplied();
                ((CartItemViewHolder)viewHolder).setItemDetails(productID,resource,title,freecoupens,productPrice,cuttedPrice,offerApplied,position);
                break;
                case CartItemModel.TOTAL_AMOUNT:

                    int totalItem=0;
                    int totalPrice=0;
                    String deliveryPrice;
                    int totalAmount;
                    int savedAmount=0;

                    for(int x=0;x<cartItemModelList.size();x++){
                        if(cartItemModelList.get(x).getTYPE()==CartItemModel.CART_ITEM){
                            totalItem++;
                            totalPrice=totalPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                        }
                    }
                    if(totalPrice>500){
                        deliveryPrice="FREE";
                        totalAmount=totalPrice;
                    }else {
                        deliveryPrice="60";
                        totalAmount=totalPrice+60;
                    }

                    ((CartTotalAmountViewHolder)viewHolder).setTotalAmount(totalItem,totalPrice,deliveryPrice,totalAmount,savedAmount);
                    break;
                    default:

        }
        if(lasrPosition<position){
            Animation animation=AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lasrPosition=position;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private ImageView freeCoupensIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;
        private LinearLayout deleteBtn;



        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.tv_product_title);
            freeCoupensIcon=itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens=itemView.findViewById(R.id.tv_free_coupen);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.tv_cutted_price);
            offersApplied=itemView.findViewById(R.id.offers_applied);
            coupensApplied=itemView.findViewById(R.id.coupens_id);
            productQuantity=itemView.findViewById(R.id.product_quantity);
            deleteBtn=itemView.findViewById(R.id.remove_item_btn);
        }
        private void setItemDetails(String productID, String resource, String title, long freecoupensNo, String productPriceText, String cuttedPriceText
                    , long offersAppliedNo , final int position){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.place_holder)).into(productImage);
            productTitle.setText(title);
            if(freecoupensNo>0)
            {
                freeCoupensIcon.setVisibility(View.VISIBLE);
                freeCoupens.setVisibility(View.VISIBLE);
                if(freecoupensNo==1){
                freeCoupens.setText("free "+freecoupensNo + " coupne");
                }
                else {
                    freeCoupens.setText("free "+freecoupensNo + " coupnes");
                }
            }else {
                freeCoupensIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if(offersAppliedNo>0){
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " offers applied");
            }else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog indialog=new Dialog(itemView.getContext());
                    indialog.setContentView(R.layout.qantuty_dalogue);
                    indialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    indialog.setCancelable(false);
                    final EditText quantityNo=indialog.findViewById(R.id.quantity_count);
                    Button cancelBtn=indialog.findViewById(R.id.cancel_btn);
                    Button OkBtn=indialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            indialog.dismiss();
                        }
                    });

                    OkBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty: " + quantityNo.getText());
                            indialog.dismiss();

                        }
                    });
                    indialog.show();
                }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailActivity.running_cart_query){}
                    ProductDetailActivity.running_cart_query=true;
                    DBqueries.removedFromCartfinal(position,itemView.getContext());
                }
            });

        }
    }
    public class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems=itemView.findViewById(R.id.total_items);
            totalItemPrice=itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_charge);
            totalAmount=itemView.findViewById(R.id.total_price);
            savedAmount=itemView.findViewById(R.id.saved_amount);

        }
        private void setTotalAmount(int totalIems,int totalItemprice,String deliveryprice
        ,int totalamount,int savedItems){
            totalItems.setText("price("+totalIems+")");
            totalItemPrice.setText("Br."+totalItemprice+"/-");
            if(deliveryPrice.equals("FREE")){
                deliveryPrice.setText(deliveryprice);
            }else {
                deliveryPrice.setText("Br. "+deliveryprice+"/-");

            }
            totalAmount.setText("Br. "+totalamount+"/-");
            cartTotalAmount.setText("Br. "+totalamount+"/-");
            savedAmount.setText("you saved "+savedItems+"/- on this order.");

        }

    }
}
