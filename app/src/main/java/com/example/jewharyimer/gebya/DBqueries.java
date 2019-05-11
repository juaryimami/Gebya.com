package com.example.jewharyimer.gebya;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {

    public static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public static FirebaseUser currentUser=firebaseAuth.getCurrentUser();

    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static List<Category_Model> category_modelList=new ArrayList<Category_Model>();
    public static List<List<HomePageModel>> lists=new ArrayList<>();
    public static List<String> loadedCategoriesName=new ArrayList<>();

    public static void loadcategories(final RecyclerView categoryRecyclerview,  final Context context){
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                                category_modelList.add(new Category_Model(documentSnapshots.get("index").toString(),documentSnapshots.get("categoryName").toString()));
                            }
                            Category_Adapter category_adapter=new Category_Adapter(category_modelList);
                            categoryRecyclerview.setAdapter(category_adapter );
                            category_adapter.notifyDataSetChanged();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public static void loadFragmentData(final RecyclerView homePageREcyclerView, final Context context, final int index, String Categoryname){
        firebaseFirestore.collection("CATEGORIES")
                .document(Categoryname.toUpperCase())
                .collection("TOP_DEALS").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                        if((long) documentSnapshots.get("view_type")==0){

                            List<SliderModel> sliderModelList=new ArrayList<>();

                            long no_of_banners= (long) documentSnapshots.get("no_of_banners");
                            for(long x=1;x<no_of_banners+1;x++){
                                sliderModelList.add(new SliderModel(documentSnapshots.get("banner_"+x).toString()
                                        ,documentSnapshots.get("banner_"+x + "_background").toString()));
                            }
                            lists.get(index).add(new HomePageModel(0,sliderModelList));

                        }else if((long) documentSnapshots.get("view_type")==1){
                            lists.get(index).add(new HomePageModel(1,documentSnapshots.get("strip_add_banner").toString()
                                    ,documentSnapshots.get("background").toString()));

                        }
                        else if((long) documentSnapshots.get("view_type")==2){
                            List<WishlistModel> viewAllProductList=new ArrayList<>();
                            List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
                            long no_of_products= (long) documentSnapshots.get("no_of_products");
                            for(long x=1;x<no_of_products+1;x++){
                                horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshots.get("product_ID_"+x).toString()
                                        ,documentSnapshots.get("product_image"+x).toString()
                                        ,documentSnapshots.get("product_title"+x).toString()
                                        ,documentSnapshots.get("product_subtitle"+x).toString()
                                        ,documentSnapshots.get("product_price"+x).toString()));


                                viewAllProductList.add(new WishlistModel(documentSnapshots.get("product_image_"+x).toString()

                                        ,documentSnapshots.get("product_full_title_"+x).toString()
                                        ,(long)documentSnapshots.get("free_coupens_"+x)
                                        ,documentSnapshots.get("average_rating_"+x).toString()
                                        ,(long)documentSnapshots.get("total_ratings_"+x)
                                        ,documentSnapshots.get("product_price_"+x).toString()
                                        ,documentSnapshots.get("cutted_price_"+x).toString()
                                        ,(boolean)documentSnapshots.get("COD_"+x)

                                ));
                            }


                            lists.get(index).add(new HomePageModel(2
                                    ,documentSnapshots.get("layout_title").toString()
                                    ,documentSnapshots.get("layout_background").toString()
                                    ,horizontalProductScrollModelList,viewAllProductList));
                        }
                        else if((long) documentSnapshots.get("view_type")==3){}


                        List<HorizontalProductScrollModel> GridlayoutlModelList=new ArrayList<>();
                        long no_of_products= (long) documentSnapshots.get("no_of_products");
                        for(long x=1;x<no_of_products+1;x++){
                            GridlayoutlModelList.add(new HorizontalProductScrollModel(documentSnapshots.get("product_ID_"+x).toString()
                                    ,documentSnapshots.get("product_image"+x).toString()
                                    ,documentSnapshots.get("product_title"+x).toString()
                                    ,documentSnapshots.get("product_subtitle"+x).toString()
                                    ,documentSnapshots.get("product_price"+x).toString()));

                        }
                        lists.get(index).add(new HomePageModel(3
                                ,documentSnapshots.get("layout_title").toString()
                                ,documentSnapshots.get("layout_background").toString()
                                ,GridlayoutlModelList));

                    }
                    HomePageAdapter homePageAdapter=new HomePageAdapter(lists.get(index));
                    homePageREcyclerView.setAdapter(homePageAdapter);
                    homePageAdapter.notifyDataSetChanged();
                    Home_Fragment.swipeRefreshLayout.setRefreshing(false);
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
