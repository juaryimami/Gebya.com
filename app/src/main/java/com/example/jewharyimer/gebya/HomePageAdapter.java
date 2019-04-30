package com.example.jewharyimer.gebya;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
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
                    int resource=homePageModelList.get(position).getResource();
                    String color=homePageModelList.get(position).getBackgroundcolor();
                    ((StripAdViewHolder)viewHolder).setStripADD(resource,color);
                    break;
                    case HomePageModel.HORIZONTAL_PRODUCT_VIIEW:
                        String thorixontalLayoutTitle=homePageModelList.get(position).getTitle();
                        List<HorizontalProductScrollModel> horizontalProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                        ((HorizontalProductViewHolder)viewHolder).setHorizontalProductTitle(horizontalProductScrollModelList,thorixontalLayoutTitle);
                     case HomePageModel.GRID_PRODUCT_VIIEW:
                         String gridLayoutTitle=homePageModelList.get(position).getTitle();
                         List<HorizontalProductScrollModel> gridProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                         ((GridProductView)viewHolder).setGridlayoutTitle(gridProductScrollModelList,gridLayoutTitle);
            default:
                    return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{
        private ViewPager bannerSliderView;
        private List<SliderModel> sliderModelList;
        private  int current_page=2;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;


         public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
             bannerSliderView=itemView.findViewById(R.id.Banner_slider_view_pager);




        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){
            SliderAdapter sliderAdapter=new SliderAdapter(sliderModelList);
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
                        PageLooper(sliderModelList);
                    }
                }
            };
            bannerSliderView.addOnPageChangeListener(onPageChangeListener);


            startbannerSlideShow(sliderModelList);
            bannerSliderView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PageLooper(sliderModelList);
                    stopbannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startbannerSlideShow(sliderModelList);
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
        private void setStripADD(int resource,String color){
            stripImage.setImageResource(resource);

        }
    }
    public class HorizontalProductViewHolder extends  RecyclerView.ViewHolder{
        private TextView horizontalProductTitle;
        private Button horizontalViewButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalProductTitle=itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewButton=itemView.findViewById(R.id.horizontal_scrol_button);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_recyclerview);
        }
        private void setHorizontalProductTitle(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){

            horizontalProductTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewButton.setVisibility(View.VISIBLE);
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
        private TextView GridlayoutTitle;
        private Button GridlayoutViewAllbutton;
        private GridView gridView;
        public GridProductView(@NonNull View itemView) {
            super(itemView);
             GridlayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
             GridlayoutViewAllbutton=itemView.findViewById(R.id.grid_product_viewAll);
             gridView=itemView.findViewById(R.id.grid_product_grid);

        }
        private void setGridlayoutTitle(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            GridlayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        }
    }
}
