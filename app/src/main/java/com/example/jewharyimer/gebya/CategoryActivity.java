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

        categoryRecyclerView=findViewById(R.id.categoryRecycview);


        ///////////////////////////////////
        //cattagoryRecyclerView=view.findViewById(R.id.testing_recyclerView);
        LinearLayoutManager testingLineatLayout=new LinearLayoutManager(this);
        testingLineatLayout.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLineatLayout);
        ////////////////////////////////////

        //List<HomePageModel> homePageModelList=new ArrayList<>();
        int listPosition=0;
        for(int x=0;x<loadedCategoriesName.size();x++){
            if(loadedCategoriesName.get(x).equals(title.toUpperCase())){
                listPosition=x;
            }
        }
        if(listPosition==0){
            loadedCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            homePageAdapter=new HomePageAdapter(lists.get(loadedCategoriesName.size()-1));
            loadFragmentData(homePageAdapter,this,0,title);

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
