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

import static com.example.jewharyimer.gebya.DBqueries.lists;
import static com.example.jewharyimer.gebya.DBqueries.loadFragmentData;
import static com.example.jewharyimer.gebya.DBqueries.loadedCategoriesName;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private List<HomePageModel> homeFakePageModelList=new ArrayList<>();
    private HomePageAdapter homePageAdapter;

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

        /////////////// home page fake list
        List<SliderModel> slideFakerModelList=new ArrayList<>();
        slideFakerModelList.add(new SliderModel("null","#ffffff"));
        slideFakerModelList.add(new SliderModel("null","#ffffff"));
        slideFakerModelList.add(new SliderModel("null","#ffffff"));
        slideFakerModelList.add(new SliderModel("null","#ffffff"));
        slideFakerModelList.add(new SliderModel("null","#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModeFakelList=new ArrayList<>();
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));

        homeFakePageModelList.add(new HomePageModel(0,slideFakerModelList));
        homeFakePageModelList.add(new HomePageModel(1,"","#fffffff"));
        homeFakePageModelList.add(new HomePageModel(2,"","#ffffff",horizontalProductScrollModeFakelList,new ArrayList<WishlistModel>()));
        homeFakePageModelList.add(new HomePageModel(3,"","#ffffff",horizontalProductScrollModeFakelList));
        /////////////// home page fake list


        categoryRecyclerView=findViewById(R.id.categoryRecycview);


        ///////////////////////////////////
        //cattagoryRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout=new LinearLayoutManager(this);
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLineatLayout);
        ////////////////////////////////////

        homePageAdapter=new HomePageAdapter(homeFakePageModelList);

        int listPosition=0;
        for(int x=0;x<loadedCategoriesName.size();x++){
            if(loadedCategoriesName.get(x).equals(title.toUpperCase())){
                listPosition=x;
            }
        }
        if(listPosition==0){
            loadedCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView,this,0,title);

        }else {
            homePageAdapter=new HomePageAdapter(lists.get(listPosition));
             }
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
