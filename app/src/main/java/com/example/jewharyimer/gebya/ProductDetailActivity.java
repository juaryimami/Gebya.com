package com.example.jewharyimer.gebya;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jewharyimer.gebya.MainActivity.showCart;
import static com.example.jewharyimer.gebya.RegisterActivity.setSignupFragment;

//import static android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating;

public class ProductDetailActivity extends AppCompatActivity {

    public static boolean running_wishlist_query=false;
    public static boolean running_rating_query=false;
    public static boolean running_cart_query=false;

    public static int initialRating;

    private ViewPager productImageViewPager;
    private TextView productTitle;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView COD;
    private TextView tv_COD;
    private TextView averageRatingMiniView;
    private TabLayout viewPagerIndicator;
    private Button coupenRedeemedbtn;
    private Dialog signinDialogue;

    //////////// rewared
    private TextView rewardTitle;
    private TextView rewardBody;
    public static String productID;
    private DocumentSnapshot documentSnapshot;
    //////////// rewared

    ////
    private LinearLayout addToCartLayout;
    private LinearLayout rattingProgressBarContainer;

    //////// coupen dialogue
    private Dialog loadingDialogue;
    public static  TextView coupenTitle;
    public static  TextView coupenExpiry;
    public static  TextView coupenBody;
    private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
    private TextView totalRating;
    private TextView totalRatingsFiguer;
    //////// coupen dialogue

    //////// rating layout

    private LinearLayout coupenRedemptionLayout;
    public static LinearLayout rateNowContainer;
    private TextView avegeRating;
    //////// rating layout
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailtabsContainer;
    private TextView productOnlyDescriptionBody;

    private ViewPager productDetailViewPaget;
    private TabLayout productDetailTablayout;
    private Button buyNowButton;
    public static Boolean addedToWishList=false;
    public static Boolean added_To_Cart=false;
    public static FloatingActionButton addToWishlistBtn;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;

    private List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;
    private int tabPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager=findViewById(R.id.product_images_viewpager);
        viewPagerIndicator=findViewById(R.id.view_pager_indicator);
        addToWishlistBtn=findViewById(R.id.add_to_wishlist_btn);
        productDetailTablayout=findViewById(R.id.product_details_tabLayout);
        productImageViewPager=findViewById(R.id.product_detail_viewPager);
        buyNowButton=findViewById(R.id.buy_now_button);
        coupenRedeemedbtn=findViewById(R.id.coupen_redemption_btn);
        productTitle=findViewById(R.id.tv_product_title);
        averageRatingMiniView=findViewById(R.id.tv_product_ratting_miniView);
        totalRatingMiniView=findViewById(R.id.total_ratting_miniView);
        productPrice=findViewById(R.id.tv_product_price);
        cuttedPrice=findViewById(R.id.tv_cutted_price);
        COD=findViewById(R.id.code_image_indicator);
        tv_COD=findViewById(R.id.tv_code_indicator);
        rewardTitle=findViewById(R.id.reward_title);
        rewardBody=findViewById(R.id.rewads_body);
        productDetailtabsContainer=findViewById(R.id.product_details_tabs_container);
        productDetailsOnlyContainer=findViewById(R.id.product_details_container);
        productOnlyDescriptionBody=findViewById(R.id.product_details_body);
        totalRating=findViewById(R.id.totalRating);
        rateNowContainer=findViewById(R.id.rate_now_container);
        totalRatingsFiguer=findViewById(R.id.total_rattings_figure);
        rattingProgressBarContainer=findViewById(R.id.ratings_progressbar_container);
        avegeRating=findViewById(R.id.avearage_ratings);
        addToCartLayout=findViewById(R.id.add_to_cart_btn);
        coupenRedemptionLayout=findViewById(R.id.coupen_redemption_layout);
        initialRating=-1;


        ///////// loading dialogue
        loadingDialogue=new Dialog(ProductDetailActivity.this);
        loadingDialogue.setContentView(R.layout.loading_progress_dialogue);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);
        loadingDialogue.show();

        ///////// loading dialogue

        firebaseFirestore=FirebaseFirestore.getInstance();

        final List<String> productImages=new ArrayList<>();
        productID=getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    documentSnapshot=task.getResult();
                    for(long x=1;x<(long)documentSnapshot.get("product_images")+1;x++){
                        productImages.add(("product_image_"+x).toString());
                    }
                    Product_Images_Adapter product_images_adapter=new Product_Images_Adapter(productImages);
                    productImageViewPager.setAdapter(product_images_adapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+") Raatings");
                    productPrice.setText("Br."+documentSnapshot.get("product_price").toString()+"/-");
                    cuttedPrice.setText("Br."+documentSnapshot.get("cutted_price").toString()+"/-");

                    if((boolean)documentSnapshot.get("COD")){
                        COD.setVisibility(View.VISIBLE);
                        tv_COD.setVisibility(View.VISIBLE);
                    }else {
                        COD.setVisibility(View.INVISIBLE);
                        tv_COD.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long)documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());
                    if((boolean)documentSnapshot.get("use_tab_layout")){

                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription=documentSnapshot.get("product_description").toString();
                        //ProductSpecificationFragment.productSpecificationModelList=new ArrayList<>();
                        productOtherDetails=documentSnapshot.get("product_other_details").toString();
                        for(long x=1;x<(long)documentSnapshot.get("total_spec_titles")+1;x++){

                            productSpecificationModelList.add(new ProductSpecificationModel(0
                            ,documentSnapshot.get("spec_title_"+x).toString()));
                            for(long y=0;y<(long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1;y++){
                                productSpecificationModelList.add(new ProductSpecificationModel(1
                                        ,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString()
                                ,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));

                            }
//66203648
                        }
                    }else {

                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_other_details").toString());
                    }
                    totalRating.setText((long)documentSnapshot.get("total_ratings")+"ratings");

                    for(int x=0;x<5;x++){
                        TextView rating= (TextView) rateNowContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                        ProgressBar progressBar= (ProgressBar) rattingProgressBarContainer.getChildAt(x);
                        int max=Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                        progressBar.setMax(max);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((6-x)+"_star"))));

                    }
                    totalRatingsFiguer.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                    avegeRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailViewPaget.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager()
                            ,productDetailTablayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));

                    if(currentUser!=null) {
                        if(DBqueries.myRating.size()==0){
                            DBqueries.loadRatinglist(ProductDetailActivity.this);
                        }
                        if (DBqueries.cartlist.size() == 0) {
                            DBqueries.loadCartList(ProductDetailActivity.this, loadingDialogue,false);
                        }
                        if (DBqueries.wishhlist.size() == 0) {
                            DBqueries.loadWishlist(ProductDetailActivity.this, loadingDialogue,false);
                        } else {
                            loadingDialogue.dismiss();
                        }


                    }else {
                        loadingDialogue.dismiss();
                    }
                    if(DBqueries.myRatedIds.contains(productID)){
                        int index=DBqueries.myRatedIds.indexOf(productID);
                        initialRating=Integer.parseInt(String.valueOf(DBqueries.myRating.get(index)))-1;
                        setRating(initialRating);
                    }

                if(DBqueries.wishhlist.contains(productID)){
                    addedToWishList=true;
                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                }else {
                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    addedToWishList=false;
                }
                    if(DBqueries.cartlist.contains(productID)){
                        added_To_Cart=true;

                    }else {
                        added_To_Cart=false;
                    }

                }else {
                    loadingDialogue.dismiss();
                    String error=task.getException().getMessage();
                    Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                }


            }
        });



        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);


        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null){
                    signinDialogue.show();
                }else {
                   // addToWishlistBtn.setEnabled(false);
                   if(!running_wishlist_query){
                       running_wishlist_query=true;

                if(addedToWishList){
                    int index=DBqueries.wishhlist.indexOf(productID);
                    DBqueries.removeFromWishlist(index,ProductDetailActivity.this);
                 //addedToWishList=false;
             addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
             }else {
                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorAccent));
                    Map<String,Object> addproduct=new HashMap<>();
                    addproduct.put("product_ID_"+String.valueOf(DBqueries.wishhlist.size()),productID);
                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                            .document("MY_WISHLIST")
                            .set(addproduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Map<String,Object> updatelistsize=new HashMap<>();
                                updatelistsize.put("list_size", (long) DBqueries.wishhlist.size());

                                firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                        .document("MY_WISHLIST")
                                        .update(updatelistsize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(DBqueries.wishhlist.size()!=0){
                                                DBqueries.wishlistModelList.add(new WishlistModel(productID,documentSnapshot.get("product_image_1").toString()
                                                        ,documentSnapshot.get("product_title").toString()
                                                        ,(long) documentSnapshot.get("free_coupens")
                                                        ,documentSnapshot.get("average_rating").toString()
                                                        ,(long)documentSnapshot.get("total_ratings")
                                                        ,documentSnapshot.get("product_price").toString()
                                                        ,documentSnapshot.get("cutted_price").toString()
                                                        ,(boolean)documentSnapshot.get("COD") ));

                                            }

                                            Toast.makeText(ProductDetailActivity.this,"Added to wishlist successfully",Toast.LENGTH_SHORT).show();
                                            addedToWishList=true;
                                            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                            DBqueries.wishhlist.add(productID);
                                        }else{
                                            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                            String error=task.getException().getMessage();
                                            Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                                        }
                                      //  addToWishlistBtn.setEnabled(true);
                                        running_wishlist_query=true;
                                    }
                                });

                            }else{
                          //      addToWishlistBtn.setEnabled(true);
                                running_wishlist_query=true;
                                String error=task.getException().getMessage();
                                Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
             }
                   }
             }
            }
        });

        productDetailViewPaget.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailTablayout));
        productDetailTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition=tab.getPosition();
                productDetailViewPaget.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /////// rating selected

        rateNowContainer=findViewById(R.id.rate_now_container);

        for(int x=0;x<rateNowContainer.getChildCount();x++){
            final int starPosition=x;
//            final int X = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser==null){
                        signinDialogue.show();
                    }else {
                         if(starPosition !=initialRating){
                        if (!running_rating_query) {
                            running_rating_query = true;
                            setRating(starPosition);
                            Map<String, Object> updaterating = new HashMap<>();

                            if (DBqueries.myRatedIds.contains(productID)) {
                                TextView oldrating = (TextView) rateNowContainer.getChildAt(5 - initialRating - 1);
                                TextView finalrating = (TextView) rateNowContainer.getChildAt(5 - starPosition - 1);

                                updaterating.put((initialRating + 1) + "_star", Long.parseLong(oldrating.getText().toString()) - 1);
                                updaterating.put((starPosition + 1) + "_star", Long.parseLong(finalrating.getText().toString()) + 1);
                                updaterating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));

                            } else {
                                updaterating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                updaterating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                updaterating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update(updaterating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Map<String, Object> myrating = new HashMap<>();
                                            if (DBqueries.myRatedIds.contains(productID)) {
                                                myrating.put("rating_" + DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                            } else {
                                                myrating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                                myrating.put("product_ID_" + DBqueries.myRatedIds.size(), productID);
                                                myrating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);

                                            }

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                                    .document("MY_RATINGS").update(myrating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBqueries.myRatedIds.contains(productID)) {
                                                            DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                            TextView oldrating = (TextView) rateNowContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalrating = (TextView) rateNowContainer.getChildAt(5 - starPosition - 1);

                                                            oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));
                                                            finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));
                                                        } else {
                                                            DBqueries.myRatedIds.add(productID);
                                                            DBqueries.myRating.add((long) starPosition + 1);

                                                            TextView rating = (TextView) rateNowContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + 1 + ")ratings");
                                                            totalRating.setText(((long) documentSnapshot.get("total_ratings") + 1) + " ratings");
                                                            totalRatingsFiguer.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                            Toast.makeText(ProductDetailActivity.this, "Thank you! for rating. ", Toast.LENGTH_SHORT).show();
                                                        }

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingfigures = (TextView) rateNowContainer.getChildAt(x);
                                                            ratingfigures.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));
                                                            ProgressBar progressBar = (ProgressBar) rattingProgressBarContainer.getChildAt(x);

                                                            if (DBqueries.myRatedIds.contains(productID)) {
                                                                int max = Integer.parseInt(totalRatingsFiguer.getText().toString());
                                                                progressBar.setMax(max);
                                                            }
                                                            progressBar.setProgress(Integer.parseInt(ratingfigures.getText().toString()));

                                                        }
                                                        initialRating = starPosition;
                                                        avegeRating.setText(calculateAverageRating(0, true));
                                                        averageRatingMiniView.setText(calculateAverageRating(0, true));
                                                        if (DBqueries.wishhlist.contains(productID) && DBqueries.wishlistModelList.size() != 0) {
                                                            int index = DBqueries.wishhlist.indexOf(productID);
                                                            // changeRatings=DBqueries.wishlistModelList.get(index);
                                                            DBqueries.wishlistModelList.get(index).setRating(avegeRating.getText().toString());
                                                            DBqueries.wishlistModelList.get(index).setTotalRating(Long.parseLong(totalRatingsFiguer.getText().toString()));


                                                        }

                                                    } else {
                                                        setRating(initialRating);
                                                        String msg = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rating_query = false;
                                                }
                                            });

                                        } else {
                                            running_rating_query = false;
                                            setRating(initialRating);
                                            String msg = task.getException().getMessage();
                                            Toast.makeText(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    }
                    }

                }
            });
        }
        /////// rating layout
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null){
                    signinDialogue.show();
                }else {
                    Intent dvIntent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                    startActivity(dvIntent);
                }
            }
        });

        addToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null){
                    signinDialogue.show();
                }else {
                if(currentUser==null){
                    signinDialogue.show();
                }else {
                    // addToWishlistBtn.setEnabled(false);
                    if(!running_wishlist_query){
                        running_wishlist_query=true;

                        if(addedToWishList){
                            int index=DBqueries.wishhlist.indexOf(productID);
                            DBqueries.removeFromWishlist(index,ProductDetailActivity.this);
                            //addedToWishList=false;
                            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }else {
                            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorAccent));
                            Map<String,Object> addproduct=new HashMap<>();
                            addproduct.put("product_ID_"+String.valueOf(DBqueries.wishhlist.size()),productID);
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                    .document("MY_WISHLIST")
                                    .set(addproduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Map<String,Object> updatelistsize=new HashMap<>();
                                        updatelistsize.put("list_size", (long) DBqueries.wishhlist.size());

                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                                .document("MY_WISHLIST")
                                                .update(updatelistsize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    if(DBqueries.wishhlist.size()!=0){
                                                        DBqueries.wishlistModelList.add(new WishlistModel(productID,documentSnapshot.get("product_image_1").toString()
                                                                ,documentSnapshot.get("product_title").toString()
                                                                ,(long) documentSnapshot.get("free_coupens")
                                                                ,documentSnapshot.get("average_rating").toString()
                                                                ,(long)documentSnapshot.get("total_ratings")
                                                                ,documentSnapshot.get("product_price").toString()
                                                                ,documentSnapshot.get("cutted_price").toString()
                                                                ,(boolean)documentSnapshot.get("COD") ));

                                                    }

                                                    Toast.makeText(ProductDetailActivity.this,"Added to wishlist successfully",Toast.LENGTH_SHORT).show();
                                                    addedToWishList=true;
                                                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                                    DBqueries.wishhlist.add(productID);
                                                }else{
                                                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                                    String error=task.getException().getMessage();
                                                    Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                                                }
                                                //  addToWishlistBtn.setEnabled(true);
                                                running_wishlist_query=true;
                                            }
                                        });

                                    }else{
                                        //      addToWishlistBtn.setEnabled(true);
                                        running_wishlist_query=true;
                                        String error=task.getException().getMessage();
                                        Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }                }
            }
        });

        ////////////// dialoges
        final Dialog redeemDialog=new Dialog(ProductDetailActivity.this);
        redeemDialog.setContentView(R.layout.coupen_redem_dialogue);
        redeemDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        redeemDialog.setCancelable(true);

        ImageView check_toggle_btn = redeemDialog.findViewById(R.id.toggle_recyclerView);
        coupenRecyclerView=redeemDialog.findViewById(R.id.coupen_recyclerView);
        selectedCoupen=redeemDialog.findViewById(R.id.selected_coupen);
        coupenTitle=redeemDialog.findViewById(R.id.coupen_title);
        coupenExpiry=redeemDialog.findViewById(R.id.coupen_validity);
        coupenBody=redeemDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = redeemDialog.findViewById(R.id.original_price);
        TextView cuttedPrice =redeemDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ProductDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupenRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList=new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("CashBack","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Discount","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));
        rewardModelList.add(new RewardModel("Buy 1 Get free 1","till 2nd june 2011","Get 20% CASHBACK on any product above Br. 500/- and below Br 2500/-."));

        MyRewardsadapter rewardsadapter=new MyRewardsadapter(rewardModelList,true);
        coupenRecyclerView.setAdapter(rewardsadapter);
        rewardsadapter.notifyDataSetChanged();

        check_toggle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogueRecyclerView();
            }
        });

        ////////////// dialogues
        coupenRedeemedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                redeemDialog.show();
            }
        });

        ////////// sign in dialodue
        signinDialogue=new Dialog(ProductDetailActivity.this);
        signinDialogue.setContentView(R.layout.signin_dialigue);
        signinDialogue.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        signinDialogue.setCancelable(true);
        Button dialogueSigninBtn=signinDialogue.findViewById(R.id.sign_in_btn);
        Button dialogueSignupBtn=signinDialogue.findViewById(R.id.sign_up_btn);

        final Intent regintent=new Intent(ProductDetailActivity.this,RegisterActivity.class);

        dialogueSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disableCloseBtn=true;
                SigninFragment.Disableclosebtn=true;
                signinDialogue.dismiss();
                setSignupFragment=false;
                startActivity(regintent);
            }
        });

        dialogueSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disableCloseBtn=true;
                SigninFragment.Disableclosebtn=true;
                signinDialogue.dismiss();
                setSignupFragment=true;
                startActivity(regintent);
            }
        });
        ////////// sign in dialogue


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            coupenRedemptionLayout.setVisibility(View.GONE);
        }else {
            coupenRedemptionLayout.setVisibility(View.VISIBLE);
        }
        if(currentUser!=null) {
            if (DBqueries.wishhlist.size() == 0) {
                DBqueries.loadWishlist(ProductDetailActivity.this, loadingDialogue,false);
            } else {
                loadingDialogue.dismiss();
            }
            if(DBqueries.myRating.size()==0){
                DBqueries.loadRatinglist(ProductDetailActivity.this);
            }
        }else {
            loadingDialogue.dismiss();
        }

        if(DBqueries.myRatedIds.contains(productID)){
            int index=DBqueries.myRatedIds.indexOf(productID);
            initialRating=Integer.parseInt(String.valueOf(DBqueries.myRating.get(index)))-1;
            setRating(initialRating);
        }
        if(DBqueries.cartlist.contains(productID)){
            added_To_Cart=true;

        }else {
            added_To_Cart=false;
        }

        if(DBqueries.wishhlist.contains(productID)){
            addedToWishList=true;
            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

        }else {
            addedToWishList=false;
        }
    }

    public static void showDialogueRecyclerView(){
        if(coupenRecyclerView.getVisibility()==View.GONE){
            coupenRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupenRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }
    }
   public static void setRating(int starPosition){
        //if(starPosition>-1) {
           for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
               ImageView starButn = (ImageView) rateNowContainer.getChildAt(x);
               starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
               if (x <= starPosition) {
                   starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("ffbb00")));
               }
           }
      // }
   }
   private String calculateAverageRating(long currentUserRating,boolean update){
        Double totalStars= Double.valueOf(0);
        for(int x=1;x<6;x++){
            TextView ratingNo= (TextView) rateNowContainer.getChildAt(5-x);
           totalStars=totalStars + (Long.parseLong(ratingNo.getText().toString())*x);
        }
        totalStars=totalStars+currentUserRating;
        if(update){
            return String.valueOf(totalStars/Long.parseLong(totalRatingsFiguer.getText().toString())).substring(0,3);
        }else {
            return String.valueOf(totalStars/Long.parseLong(totalRatingsFiguer.getText().toString()+ 1)).substring(0,3);
        }
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            finish();
            return true;
        }else if (id == R.id.main_search_icon) {
            // todo:search
            return true;
        }/*else if(id==R.id.main_notification_icon){


        }*/else if(id==R.id.main_cart_icon){
            if(currentUser==null){
                signinDialogue.show();
            }else {
            Intent cartIntent=new Intent(ProductDetailActivity.this,MainActivity.class);
            showCart=true;
            startActivity(cartIntent);
            }
            return true;
        }else if(id==R.id.nav_sign_out){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
