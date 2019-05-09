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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.jewharyimer.gebya.MainActivity.showCart;

//import static android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating;

public class ProductDetailActivity extends AppCompatActivity {

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

    //////////// rewared
    private TextView rewardTitle;
    private TextView rewardBody;
    public static List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
    //////////// rewared

    ////
    private LinearLayout rattingProgressBarContainer;

    //////// coupen dialogue
    public static  TextView coupenTitle;
    public static  TextView coupenExpiry;
    public static  TextView coupenBody;
    private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
    private TextView totalRating;
    private TextView totalRatingsFiguer;
    //////// coupen dialogue

    //////// rating layout

    private LinearLayout rateNowContainer;
    //////// rating layout
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailtabsContainer;
    private TextView productOnlyDescriptionBody;

    private ViewPager productDetailViewPaget;
    private TabLayout productDetailTablayout;
    private Button buyNowButton;
    private static Boolean addedToWishList=false;
    private FloatingActionButton addToWishlistBtn;
    private FirebaseFirestore firebaseFirestore;
    public static String productDescription;
    public static String productOtherDetails;
    public static int tabPosition=-1;

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

        firebaseFirestore=FirebaseFirestore.getInstance();

        final List<String> productImages=new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document("jksjfsljfiwj //should be the  outo id ")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
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
                            for(long y=0;y<(long)documentSnapshot.get("spec_title_4_total_fields")+1;y++){
                                productSpecificationModelList.add(new ProductSpecificationModel(1
                                        ,documentSnapshot.get("spec_title_"+1+"_field_"+1+"_name").toString()
                                ,documentSnapshot.get("spec_title_"+1+"_field_"+1+"_value").toString()));

                            }
//66203648
                        }
                    }else {

                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_other_details").toString());
                    }
                    totalRating.setText((long)documentSnapshot.get("total_ratings")+"ratings");

                    for(int x=1;x<6;x++){
                        TextView rating= (TextView) rateNowContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((6-x)+"_star")));

                        ProgressBar progressBar= (ProgressBar) rattingProgressBarContainer.getChildAt(x);
                        int max=Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                        progressBar.setMax(max);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((6-x)+"_star"))));

                    }
                    totalRatingsFiguer.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                }


            }
        });



        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);


        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(addedToWishList){
                 addedToWishList=false;
             addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
             }else {
                 addedToWishList=true;
                 addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

             }
            }
        });

        productDetailViewPaget.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailTablayout.getTabCount()));
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
                    setRating(starPosition);

                }
            });
        }
        /////// rating layout
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dvIntent=new Intent(ProductDetailActivity.this,DeliveryActivity.class);
                startActivity(dvIntent);
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
   public void setRating(int starPosition){
        for(int x=0;x<rateNowContainer.getChildCount();x++){
            ImageView starButn=(ImageView)rateNowContainer.getChildAt(x);
            starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x<=starPosition){
                starButn.setImageTintList(ColorStateList.valueOf(Color.parseColor("ffbb00")));
            }
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
        }else if(id==R.id.main_notification_icon){
            Intent cartIntent=new Intent(ProductDetailActivity.this,MainActivity.class);
            showCart=true;
            startActivity(cartIntent);
            return true;

        }else if(id==R.id.main_cart_icon){
            // todo:notification
            return true;
        }else if(id==R.id.nav_sign_out){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
