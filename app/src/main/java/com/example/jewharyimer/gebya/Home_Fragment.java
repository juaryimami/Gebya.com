package com.example.jewharyimer.gebya;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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

import com.example.jewharyimer.gebya.Category_Adapter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private Category_Adapter category_adapter;
    RecyclerView cattagoryRecyclerView;

    /*private ViewPager bannerSliderView;
    private List<SliderModel> sliderModelList;

    private int current_page=2;
    private Timer timer;
    final private long DELAY_TIME=3000;
    final private long PERIOD_TIME=3000;
    *//////// Add_strip
    /*private ImageView stripImage;
    private ConstraintLayout addStripContainer;
    *//////// Add_strip

    ////////////   horizontal product layout
    /*private TextView horizontalLayoutTitle;
    private Button horizontalLayoutButton;
    private RecyclerView horizontalRecyclerView;*/
    ////////////   horizontal product layout


    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_, container, false);

        categoryRecyclerView=view.findViewById(R.id.catagiry_recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<Category_Model> category_modelList=new ArrayList<Category_Model>();
        category_modelList.add(new Category_Model("link","Home"));
        category_modelList.add(new Category_Model("link","Electronics"));
        category_modelList.add(new Category_Model("link","Appliance"));
        category_modelList.add(new Category_Model("link","Furniture"));
        category_modelList.add(new Category_Model("link","Fashion"));
        category_modelList.add(new Category_Model("link","Toys"));
        category_modelList.add(new Category_Model("link","Sports"));
        category_modelList.add(new Category_Model("link","Wall Arts"));
        category_modelList.add(new Category_Model("link","Books"));
        category_modelList.add(new Category_Model("link","Shoes"));

      category_adapter =new Category_Adapter(category_modelList);
      categoryRecyclerView.setAdapter(category_adapter);
      category_adapter.notifyDataSetChanged();

      //bannerSliderView=view.findViewById(R.id.Banner_slider_view_pager);
      List<SliderModel>sliderModelList=new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.custom_error_icon,"#077AE4"));


        sliderModelList.add(new SliderModel(R.drawable.email_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.red_email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_launcher_background,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_launcher_foreground,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.my_cart,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.profile,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.custom_error_icon,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.email_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.red_email,"#077AE4"));


       /* SliderAdapter sliderAdapter=new SliderAdapter(sliderModelList);
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
               PageLooper();
           }
          }
      };
      bannerSliderView.addOnPageChangeListener(onPageChangeListener);


      startbannerSlideShow();
      bannerSliderView.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              PageLooper();
              stopbannerSlideShow();
              if(event.getAction()==MotionEvent.ACTION_UP){
                  startbannerSlideShow();
              }
              return false;
          }
      });
*/
         ////////////// Add Strip image
        /*stripImage=view.findViewById(R.id.strip_ad_image);
        addStripContainer=view.findViewById(R.id.strip_ad_container);
        stripImage.setImageResource(R.drawable.im9);
        addStripContainer.setBackgroundColor(Color.parseColor("#000000"));*/
        ////////////// Add Strip image
        ////////////   horizontal product layout
       /* horizontalLayoutTitle=view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalLayoutButton=view.findViewById(R.id.horizontal_scrol_button);
        horizontalRecyclerView=view.findViewById(R.id.horizontal_scroll_recyclerview);
       */ ////////////   horizontal product layout

        List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im1,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im2,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));

        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im3,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im4,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im5,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));

        /*HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();
        *///////////////// horizontal product layout
        /////////////     grid product layout

        /*TextView GridlayoutTitle=view.findViewById(R.id.grid_product_layout_title);
        TextView GridlayoutViewAllbutton=view.findViewById(R.id.grid_product_viewAll);
        GridView gridView=view.findViewById(R.id.grid_product_grid);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
*/
        ///////////////////////////////////
        cattagoryRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout=new LinearLayoutManager(getContext());
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        cattagoryRecyclerView.setLayoutManager(testingLineatLayout);
        ////////////////////////////////////

        List<HomePageModel> homePageModelList=new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.im4,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.im4,"#ff0000"));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductScrollModelList));
        //homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.im5,"#ffff00"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));


        HomePageAdapter homePageAdapter=new HomePageAdapter(homePageModelList);
        cattagoryRecyclerView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();

        return view;
    }





   /* private void PageLooper(){
    if(current_page==sliderModelList.size()-2){
       current_page=2;
       bannerSliderView.setCurrentItem(current_page,false);
        }
        if(current_page==1){
            current_page=sliderModelList.size()-3;
            bannerSliderView.setCurrentItem(current_page,false);
        }
    }
    private void startbannerSlideShow(){
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
*/
}
