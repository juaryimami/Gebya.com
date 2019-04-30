package com.example.jewharyimer.gebya;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        categoryRecyclerView=findViewById(R.id.categoryRecycview);

        /*List<Category_Model> category_modelList=new ArrayList<Category_Model>();
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
*/
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



        List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im1,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im2,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));

        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im3,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im4,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im5,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.im6,"Raya Habesha Kemis", "New Fashion","Br.7999/-"));


        ///////////////////////////////////
        //cattagoryRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout=new LinearLayoutManager(this);
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLineatLayout);
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
        categoryRecyclerView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.main_search_icon){
            return true;
        }else if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
