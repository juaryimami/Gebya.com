package com.example.jewharyimer.gebya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewAllfActivity extends AppCompatActivity {

    private RecyclerView viewAllrecyclerView;
    private GridView gridView;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    public static List<WishlistModel> wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_allf);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewAllrecyclerView=findViewById(R.id.viewallRecyclerView);
        gridView=findViewById(R.id.grid_view);

        int layout_code=getIntent().getIntExtra("layout_code",-1);

        if(layout_code==0) {
            viewAllrecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            viewAllrecyclerView.setLayoutManager(linearLayout);

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
            viewAllrecyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        }else if(layout_code==1) {

            gridView.setVisibility(View.VISIBLE);

            GridProductLayoutAdapter gridLayoutManager = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridLayoutManager);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
