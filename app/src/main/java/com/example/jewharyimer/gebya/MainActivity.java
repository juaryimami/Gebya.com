package com.example.jewharyimer.gebya;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;

    private static final int ORDERS_FRAGMENT=2;
    private static final int WISHLIST_FRAGMENT=3;
    private static final int REWARD_FRAGMENT=4;
    private static final int ACCOUNT_FRAGMENT=5;
    private Window window;

    private FrameLayout frameLayout;

    private ImageView ActionBarLogo;

    private NavigationView navigationView;

    private static int currentFragment=-1;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBarLogo=findViewById(R.id.action_bar_logo);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout=findViewById(R.id.main_frame1);
        setFragment(new Home_Fragment(),HOME_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(currentFragment==HOME_FRAGMENT){
            super.onBackPressed();
            }else {
                ActionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new Home_Fragment(),HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment==HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.nav_my_mall){
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_my_mall){
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
            ActionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new Home_Fragment(),HOME_FRAGMENT);
        }

        if (id == R.id.nav_my_orders) {
            gotFragment("My Orders",new MyOrderFragment(),ORDERS_FRAGMENT);
        } else if (id == R.id.nav_my_reward) {
            gotFragment("My Reward",new MyRewardsFragment(),REWARD_FRAGMENT);

        } else if (id == R.id.nav_my_cart) {
            gotFragment("My cart",new MyCartFragment(),CART_FRAGMENT);

        } else if (id == R.id.nav_my_wishlist) {
            gotFragment("My WishList",new WishlistFragment(),WISHLIST_FRAGMENT);

        } else if (id == R.id.nav_my_account) {
            gotFragment("My Account", new MyAccountFragment(),ACCOUNT_FRAGMENT);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotFragment(String title,Fragment fragment,int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        ActionBarLogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo==CART_FRAGMENT){
        navigationView.getMenu().getItem(3).setChecked(true);}
    }

    private  void setFragment(Fragment fragment,int fragmentNo){
        if(fragmentNo!=currentFragment){
            if(fragmentNo==REWARD_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#5b04b1"));
                toolbar.setBackgroundColor(Color.parseColor("#5b04b1"));
            }else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        currentFragment=fragmentNo;
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
        }
    }
}
