package com.example.jewharyimer.gebya;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

public class DBqueries {


    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static List<Category_Model> category_modelList=new ArrayList<Category_Model>();
    public static List<List<HomePageModel>> lists=new ArrayList<>();
    public static List<String> loadedCategoriesName=new ArrayList<>();
    public static List<WishlistModel> wishlistModelList=new ArrayList<>();
    public static List<String> wishhlist=new ArrayList<>();
    public static  List<String> myRatedIds=new ArrayList<>();
    public static List<Long> myRating=new ArrayList<>();

    public static List<String> cartlist=new ArrayList<>();
    public static List<CartItemModel> cartItemModelList=new ArrayList<>();

    public static void loadcategories(final RecyclerView categoryRecyclerview,  final Context context){
        category_modelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                                category_modelList.add(new Category_Model(documentSnapshots.get("index").toString(),documentSnapshots.get("categoryName").toString()));
                            }
                            Category_Adapter category_adapter=new Category_Adapter(category_modelList);
                            categoryRecyclerview.setAdapter(category_adapter );
                            category_adapter.notifyDataSetChanged();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public static void loadFragmentData(final RecyclerView homePageREcyclerView, final Context context, final int index, String Categoryname){
        firebaseFirestore.collection("CATEGORIES")
                .document(Categoryname.toUpperCase())
                .collection("TOP_DEALS").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                        if((long) documentSnapshots.get("view_type")==0){

                            List<SliderModel> sliderModelList=new ArrayList<>();

                            long no_of_banners= (long) documentSnapshots.get("no_of_banners");
                            for(long x=1;x<no_of_banners+1;x++){
                                sliderModelList.add(new SliderModel(documentSnapshots.get("banner_"+x).toString()
                                        ,documentSnapshots.get("banner_"+x + "_background").toString()));
                            }
                            lists.get(index).add(new HomePageModel(0,sliderModelList));

                        }else if((long) documentSnapshots.get("view_type")==1){
                            lists.get(index).add(new HomePageModel(1,documentSnapshots.get("strip_add_banner").toString()
                                    ,documentSnapshots.get("background").toString()));

                        }
                        else if((long) documentSnapshots.get("view_type")==2){
                            List<WishlistModel> viewAllProductList=new ArrayList<>();
                            List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
                            long no_of_products= (long) documentSnapshots.get("no_of_products");
                            for(long x=1;x<no_of_products+1;x++){
                                horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshots.get("product_ID_"+x).toString()
                                        ,documentSnapshots.get("product_image"+x).toString()
                                        ,documentSnapshots.get("product_title"+x).toString()
                                        ,documentSnapshots.get("product_subtitle"+x).toString()
                                        ,documentSnapshots.get("product_price"+x).toString()));


                                viewAllProductList.add(new WishlistModel(documentSnapshots.get("product_ID_"+x).toString()
                                        ,documentSnapshots.get("product_image_"+x).toString()

                                        ,documentSnapshots.get("product_full_title_"+x).toString()
                                        ,(long)documentSnapshots.get("free_coupens_"+x)
                                        ,documentSnapshots.get("average_rating_"+x).toString()
                                        ,(long)documentSnapshots.get("total_ratings_"+x)
                                        ,documentSnapshots.get("product_price_"+x).toString()
                                        ,documentSnapshots.get("cutted_price_"+x).toString()
                                        ,(boolean)documentSnapshots.get("COD_"+x)

                                ));
                            }


                            lists.get(index).add(new HomePageModel(2
                                    ,documentSnapshots.get("layout_title").toString()
                                    ,documentSnapshots.get("layout_background").toString()
                                    ,horizontalProductScrollModelList,viewAllProductList));
                        }
                        else if((long) documentSnapshots.get("view_type")==3){}


                        List<HorizontalProductScrollModel> GridlayoutlModelList=new ArrayList<>();
                        long no_of_products= (long) documentSnapshots.get("no_of_products");
                        for(long x=1;x<no_of_products+1;x++){
                            GridlayoutlModelList.add(new HorizontalProductScrollModel(documentSnapshots.get("product_ID_"+x).toString()
                                    ,documentSnapshots.get("product_image"+x).toString()
                                    ,documentSnapshots.get("product_title"+x).toString()
                                    ,documentSnapshots.get("product_subtitle"+x).toString()
                                    ,documentSnapshots.get("product_price"+x).toString()));

                        }
                        lists.get(index).add(new HomePageModel(3
                                ,documentSnapshots.get("layout_title").toString()
                                ,documentSnapshots.get("layout_background").toString()
                                ,GridlayoutlModelList));

                    }
                    HomePageAdapter homePageAdapter=new HomePageAdapter(lists.get(index));
                    homePageREcyclerView.setAdapter(homePageAdapter);
                    homePageAdapter.notifyDataSetChanged();
                    Home_Fragment.swipeRefreshLayout.setRefreshing(false);
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static void loadWishlist(final Context context, final Dialog dialog,final boolean loadProductData){
        wishhlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_WISHLIST").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        for(long x=0;x< (long)task.getResult().get("list_size");x++){
                            wishhlist.add(task.getResult().get("product_ID"+x).toString());

                            if(DBqueries.wishhlist.contains(ProductDetailActivity.productID)){
                                ProductDetailActivity.addedToWishList=true;
                                if(ProductDetailActivity.addToWishlistBtn != null) {
                                    ProductDetailActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                                }
                            }else {
                                if(ProductDetailActivity.addToWishlistBtn!=null) {
                                    ProductDetailActivity.addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                }
                                ProductDetailActivity.addedToWishList=false;
                            }

                            if(loadProductData) {
                                wishlistModelList.clear();
                                final String productId=task.getResult().get("product_ID_"+x).toString();
                                firebaseFirestore.collection("PRODUCT").document(productId)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()) {
                                            wishlistModelList.add(new WishlistModel(productId,task.getResult().get("product_image_1").toString()
                                                    , task.getResult().get("product_title").toString()
                                                    , (long) task.getResult().get("free_coupens")
                                                    , task.getResult().get("average_rating").toString()
                                                    , (long) task.getResult().get("total_ratings")
                                                    , task.getResult().get("product_price").toString()
                                                    , task.getResult().get("cutted_price").toString()
                                                    , (boolean) task.getResult().get("COD")

                                            ));
                                            WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                    }else {
                        String error=task.getException().getMessage();
                        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    }
                });
    }
    public static void removeFromWishlist(final int index, final Context context){
        final String removedProductID=wishhlist.get(index);
        wishhlist.remove(index);
        Map<String,Object> updateWishlist=new HashMap<>();
        for(int x=0;x<wishhlist.size();x++){
            updateWishlist.put("product_ID_"+x,wishhlist.get(x));
        }
        updateWishlist.put("list_size",(long)wishhlist.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){
               if(wishhlist.size()!=0){
                   wishhlist.remove(index);
                   WishlistFragment.wishlistAdapter.notifyDataSetChanged();
               }
               ProductDetailActivity.addedToWishList=false;
               Toast.makeText(context,"removed successfully!",Toast.LENGTH_SHORT).show();

           }else {
               if(ProductDetailActivity.addToWishlistBtn!=null){
               ProductDetailActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorAccent));
               }
               wishhlist.add(index,removedProductID);
               String error=task.getException().getMessage();
               Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
           }

                ProductDetailActivity.running_wishlist_query=false;
            }
        });
    }
    public static void loadRatinglist(final Context context){
        if(!ProductDetailActivity.running_rating_query) {
            ProductDetailActivity.running_rating_query=true;
            myRatedIds.clear();
            myRating.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_RATINGS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));

                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailActivity.productID)
                                    ) {
                                ProductDetailActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if(ProductDetailActivity.rateNowContainer != null){
                                ProductDetailActivity.setRating(ProductDetailActivity.initialRating);
                                }
                            }

                        }
                    } else {
                        String msg = task.getException().getMessage();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailActivity.running_rating_query=false;
                }
            });
        }
    }

    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgecount){
        cartlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_CART").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            for(long x=0;x< (long)task.getResult().get("list_size");x++){
                                cartlist.add(task.getResult().get("product_ID"+x).toString());

                                if(DBqueries.cartlist.contains(ProductDetailActivity.productID)){
                                    ProductDetailActivity.added_To_Cart=true;

                                }else {
                                    if(ProductDetailActivity.addToWishlistBtn!=null) {
                                        ProductDetailActivity.addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                    }
                                    ProductDetailActivity.addedToWishList=false;
                                }

                                if(loadProductData) {
                                    cartItemModelList.clear();
                                    final String productId=task.getResult().get("product_ID_"+x).toString();
                                    firebaseFirestore.collection("PRODUCT").document(productId)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                            if (task.isSuccessful()) {
                                                int index=0;
                                                if(cartlist.size() >= 2){
                                                    index=cartlist.size()-2;
                                                }
                                                cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM,productId,task.getResult().get("product_image_1").toString()
                                                        , (long)task.getResult().get("free_coupens")
                                                        , task.getResult().get("product_price").toString()
                                                        , task.getResult().get("cutted_price").toString()
                                                        , 1
                                                        , (long)0
                                                        , (long) 0

                                                ));
                                                if(cartlist.size()==0){
                                                    cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                }
                                                if(cartlist.size()==0){
                                                    cartItemModelList.clear();
                                                }

                                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }


                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }
    public static void clearData(){
        category_modelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
        wishhlist.clear();
        wishlistModelList.clear();
        cartlist.clear();
        cartItemModelList.clear();
    }
    public static void removedFromCartfinal(final int index, final Context context){
        final String removedProductID=cartlist.get(index);
        cartlist.remove(index);
        Map<String,Object> updateCartList=new HashMap<>();
        for(int x=0;x<cartlist.size();x++){
            updateCartList.put("product_ID_"+x,cartlist.get(x));
        }
        updateCartList.put("list_size",(long)cartlist.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(cartItemModelList.size()!=0){
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if(cartlist.size()==0){
                        cartItemModelList.clear();
                    }
                    Toast.makeText(context,"removed successfully!",Toast.LENGTH_SHORT).show();


                }else {
                    cartlist.add(index,removedProductID);
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }

                ProductDetailActivity.running_cart_query=false;
            }
        });}
}
