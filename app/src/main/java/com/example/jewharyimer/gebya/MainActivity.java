package com.example.jewharyimer.gebya;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import static com.example.jewharyimer.gebya.DBqueries.currentUser;
import static com.example.jewharyimer.gebya.RegisterActivity.setSignupFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDERS_FRAGMENT=2;
    private static final int WISHLIST_FRAGMENT=3;
    private static final int REWARD_FRAGMENT=4;
    private static final int ACCOUNT_FRAGMENT=5;
    public static boolean showCart=false;
    public static Activity mainActivity;

    private Dialog signinDialogue;
    private Window window;

    private FrameLayout frameLayout;

    private ImageView ActionBarLogo;

    private NavigationView navigationView;

    private int currentFragment=-1;
    public static DrawerLayout drawer;

    private Toolbar toolbar;

    private FirebaseUser currentUser;
    private TextView badge_count;
    private int scrolFlags;
    private AppBarLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBarLogo = findViewById(R.id.action_bar_logo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

         params=(AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrolFlags=params.getScrollFlags();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_frame1);
        if (showCart) {
            mainActivity=this;
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            setFragment(new Home_Fragment(), HOME_FRAGMENT);
        }

         signinDialogue=new Dialog(MainActivity.this);
        signinDialogue.setContentView(R.layout.signin_dialigue);
        signinDialogue.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        signinDialogue.setCancelable(true);
        Button dialogueSigninBtn=signinDialogue.findViewById(R.id.sign_in_btn);
        Button dialogueSignupBtn=signinDialogue.findViewById(R.id.sign_up_btn);

        final Intent regintent=new Intent(MainActivity.this,RegisterActivity.class);

        dialogueSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disableCloseBtn=true;
                SigninFragment.Disableclosebtn=true;
                signinDialogue.dismiss();
                setSignupFragment=false;
                startActivity(regintent);
            }
        });

        dialogueSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disableCloseBtn=true;
                SigninFragment.Disableclosebtn=true;
                signinDialogue.dismiss();
                setSignupFragment=true;
                startActivity(regintent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
        invalidateOptionsMenu();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(currentFragment==HOME_FRAGMENT){
                currentFragment=-1;
            super.onBackPressed();
            }else {
                if(showCart){
                    mainActivity=null;
                    showCart=false;
                    finish();
                }else {
                ActionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new Home_Fragment(),HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment==HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
            MenuItem cartItem=menu.findItem(R.id.main_cart_icon);
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badge_icon=cartItem.getActionView().findViewById(R.id.badge_icon);
            badge_icon.setImageResource(R.drawable.my_cart);
             badge_count=cartItem.getActionView().findViewById(R.id.badge_count);

             if(currentUser!=null){
                 if (DBqueries.cartlist.size() == 0) {
                     DBqueries.loadCartList(MainActivity.this, new Dialog(MainActivity.this),false,badge_count,new TextView(MainActivity.this));
                 } else {
                         badge_count.setVisibility(View.VISIBLE);
                     if(DBqueries.cartlist.size()<99){
                         badge_count.setText(String.valueOf(DBqueries.cartlist.size()));
                     }else {
                         badge_count.setText("99");
                     }}
             }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser==null){
                        signinDialogue.show();
                    }else {
                        gotFragment("MY CART", new MyCartFragment(), CART_FRAGMENT);
                    }
                }
            });

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
            if(currentUser==null){
            signinDialogue.show();
            }else {
                gotFragment("MY CART", new MyCartFragment(), CART_FRAGMENT);
            }
            return true;
        }else if(id==android.R.id.home){
            if(showCart){
                mainActivity=null;
            showCart=false;
            finish();
            return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    MenuItem menuItem;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

         menuItem=item;
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

        if(currentUser!=null) {

            drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_my_mall) {
                        //getSupportActionBar().setDisplayShowTitleEnabled(false);
                        ActionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new Home_Fragment(), HOME_FRAGMENT);
                    }

                    if (id == R.id.nav_my_orders) {
                        gotFragment("My Orders", new MyOrderFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.nav_my_reward) {
                        gotFragment("My Reward", new MyRewardsFragment(), REWARD_FRAGMENT);

                    } else if (id == R.id.nav_my_cart) {
                        gotFragment("My cart", new MyCartFragment(), CART_FRAGMENT);

                    } else if (id == R.id.nav_my_wishlist) {
                        gotFragment("My WishList", new WishlistFragment(), WISHLIST_FRAGMENT);

                    } else if (id == R.id.nav_my_account) {
                        gotFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);

                    } else if (id == R.id.nav_sign_out) {
                        FirebaseAuth.getInstance().signOut();
                        DBqueries.clearData();
                        Intent regintent=new Intent (MainActivity.this,RegisterActivity.class);
                        startActivity(regintent);
                        finish();
                    }
                }
            });


            return true;
        }else{
            signinDialogue.show();
            return false;
        }

    }

    private void gotFragment(String title,Fragment fragment,int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        ActionBarLogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo==CART_FRAGMENT || showCart){
        navigationView.getMenu().getItem(3).setChecked(true);
        params.setScrollFlags(0);
        }else {
            params.setScrollFlags(scrolFlags);
        }
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
