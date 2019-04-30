package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

//import static android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;

    //////// rating layout

    private LinearLayout rateNowContainer;
    //////// rating layout

    private ViewPager productDetailViewPaget;
    private TabLayout productDetailTablayout;
    private Button buyNowButton;

    private static Boolean addedToWishList=false;
    private FloatingActionButton addToWishlistBtn;

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

        List<Integer> productImages=new ArrayList<>();
        productImages.add(R.drawable.im1);// product image
        productImages.add(R.drawable.im2);//image
        productImages.add(R.drawable.im3);//banner
        productImages.add(R.drawable.im4);// strip add
        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);

        Product_Images_Adapter product_images_adapter=new Product_Images_Adapter(productImages);
        productImageViewPager.setAdapter(product_images_adapter);

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
            // todo:cart
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
