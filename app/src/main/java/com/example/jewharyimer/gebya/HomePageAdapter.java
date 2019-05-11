package com.example.jewharyimer.gebya;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lasrPosition=-1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
       switch (homePageModelList.get(position).getType()){
           case 0:
               return HomePageModel.BANNER_SLIDER;
           case 1:
               return HomePageModel.BANNER_AD_SLIDER;
           case 2:
               return HomePageModel.HORIZONTAL_PRODUCT_VIIEW;
           case 3:
               return HomePageModel.GRID_PRODUCT_VIIEW;
               default:
                   return -1;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannersliderview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_add_layout,viewGroup,false);
                return new BannerSliderViewHolder(bannersliderview);
                case HomePageModel.BANNER_AD_SLIDER:
                    View stripAdvview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_add_layout,viewGroup,false);
                    return new StripAdViewHolder(stripAdvview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIIEW:
                View horixontalproductview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout,viewGroup,false);
                return new HorizontalProductViewHolder(horixontalproductview);
            case HomePageModel.GRID_PRODUCT_VIIEW:
                View gridproductview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout,viewGroup,false);
                return new GridProductView(gridproductview);
                default:
                    return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList=homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
                case HomePageModel.BANNER_AD_SLIDER:
                    String resource=homePageModelList.get(position).getResource();
                    String color=homePageModelList.get(position).getBackgroundcolor();
                    ((StripAdViewHolder)viewHolder).setStripADD(resource,color);
                    break;
                    case HomePageModel.HORIZONTAL_PRODUCT_VIIEW:
                        String layout_color=homePageModelList.get(position).getBackgroundcolor();
                        String thorixontalLayoutTitle=homePageModelList.get(position).getTitle();
                        List<WishlistModel> viewAllProductList=homePageModelList.get(position).getViewProductlList();
                        List<HorizontalProductScrollModel> horizontalProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                        ((HorizontalProductViewHolder)viewHolder).setHorizontalProductTitle(horizontalProductScrollModelList
                                ,thorixontalLayoutTitle,layout_color,viewAllProductList);
                     case HomePageModel.GRID_PRODUCT_VIIEW:
                         String gridLayoutColor=homePageModelList.get(position).getBackgroundcolor();
                         String gridLayoutTitle=homePageModelList.get(position).getTitle();
                         List<HorizontalProductScrollModel> gridProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                         ((GridProductView)viewHolder).setGridlayoutTitle(gridProductScrollModelList,gridLayoutTitle,gridLayoutColor);
            default:
                    return;
        }
        if(lasrPosition<position){
        Animation animation=AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.fade_in);
        viewHolder.itemView.setAnimation(animation);
        lasrPosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{
        private ViewPager bannerSliderView;
        private List<SliderModel> sliderModelList;
        private  int current_page;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;
        private List<SliderModel> arrangedList;


         public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
             bannerSliderView=itemView.findViewById(R.id.Banner_slider_view_pager);




        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){

             current_page=2;
             if(timer!=null){
                 timer.cancel();
             }

             arrangedList=new ArrayList<>();
             for(int x=0;x<sliderModelList.size();x++){
                 arrangedList.add(x,sliderModelList.get(x));
             }

             arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
             arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
             arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter=new SliderAdapter(arrangedList);
            bannerSliderView.setAdapter(sliderAdapter);
            bannerSliderView.setClipToPadding(false);
            bannerSliderView.setPageMargin(20);

            bannerSliderView.setCurrentItem(current_page);

            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    current_page=i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if(i==ViewPager.SCROLL_STATE_IDLE){
                        PageLooper(arrangedList);
                    }
                }
            };
            bannerSliderView.addOnPageChangeListener(onPageChangeListener);


            startbannerSlideShow(arrangedList);
            bannerSliderView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PageLooper(arrangedList);
                    stopbannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startbannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });
        }
        private void PageLooper(List<SliderModel> sliderModelList){
            if(current_page==sliderModelList.size()-2){
                current_page=2;
                bannerSliderView.setCurrentItem(current_page,false);
            }
            if(current_page==1){
                current_page=sliderModelList.size()-3;
                bannerSliderView.setCurrentItem(current_page,false);
            }
        }
        private void startbannerSlideShow(final List<SliderModel> sliderModelList){
            final Handler handler=new Handler();
            final Runnable update=new Runnable() {
                @Override
                public void run() {
                    if(current_page>=sliderModelList.size()){
                        current_page=1;
                    }
                    bannerSliderView.setCurrentItem(current_page++,true);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);
        }
        private void stopbannerSlideShow(){
            timer.cancel();
        }

    }
    public class StripAdViewHolder extends RecyclerView.ViewHolder{
        private ImageView stripImage;
        private ConstraintLayout stripAddContainer;

        public StripAdViewHolder(@NonNull View itemView) {
            super(itemView);
            stripImage=itemView.findViewById(R.id.strip_ad_image);
            stripAddContainer=itemView.findViewById(R.id.strip_ad_container);
        }
        private void setStripADD(String resource,String color){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.place_holder)).into(stripImage);
            stripAddContainer.setBackgroundColor(Color.parseColor(color));

        }
    }
    public class HorizontalProductViewHolder extends  RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView horizontalProductTitle;
        private Button horizontalViewButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            horizontalProductTitle=itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewButton=itemView.findViewById(R.id.horizontal_scrol_button);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_recyclerview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductTitle(List<HorizontalProductScrollModel> horizontalProductScrollModelList
                , final String title, String color, final List<WishlistModel> wishlistModelList){

            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalProductTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewButton.setVisibility(View.VISIBLE);
                horizontalViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllfActivity.wishlistModelList=wishlistModelList;
                        Intent newhorzontalintent=new Intent(itemView.getContext(),ViewAllfActivity.class);
                        newhorzontalintent.putExtra("layout_code",0);
                        newhorzontalintent.putExtra("title",title);
                        itemView.getContext().startActivity(newhorzontalintent);
                    }
                });
            }else {
                horizontalViewButton.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }
    public class GridProductView extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView GridlayoutTitle;
        private Button GridlayoutViewAllbutton;
        private GridView gridView;
        private GridLayout grid_produc_grid;
        public GridProductView(@NonNull View itemView) {
            super(itemView);
             container=itemView.findViewById(R.id.container);
             GridlayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
             GridlayoutViewAllbutton=itemView.findViewById(R.id.grid_product_viewAll);
             grid_produc_grid=itemView.findViewById(R.id.grid_view);

        }
        private void setGridlayoutTitle(final List<HorizontalProductScrollModel> horizontalProductScrollModelList,
                                        final String title, String color){

            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

            GridlayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

            for(int i=0;i<4;i++){
            ImageView imageView=grid_produc_grid.getChildAt(1).findViewById(R.id.h_s_product_image);
            TextView productTitle=grid_produc_grid.getChildAt(i).findViewById(R.id.h_s_product_text);
            TextView productDiscription=grid_produc_grid.getChildAt(i).findViewById(R.id.h_s_product_desctiption);
            TextView productPrice=grid_produc_grid.getChildAt(i).findViewById(R.id.h_s_product_price);

            Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(i).getProductImage())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder)).into(imageView);
            productTitle.setText(horizontalProductScrollModelList.get(i).getProductTitle());
            productDiscription.setText(horizontalProductScrollModelList.get(i).getProductDescription());
            productPrice.setText("BR. "+horizontalProductScrollModelList.get(i).getProductPrice()+"/-");

            if(!title.equals("")){
            grid_produc_grid.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            grid_produc_grid.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent=new Intent(itemView.getContext(),ProductDetailActivity.class);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
            }

            }

            if(!title.equals("")) {
                GridlayoutViewAllbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllfActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                        Intent newhorzontalintent = new Intent(itemView.getContext(), ViewAllfActivity.class);
                        newhorzontalintent.putExtra("layout_code", 1);
                        newhorzontalintent.putExtra("title", title);
                        itemView.getContext().startActivity(newhorzontalintent);

                    }
                });
            }
        }
    }
}
