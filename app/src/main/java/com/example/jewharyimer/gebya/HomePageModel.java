package com.example.jewharyimer.gebya;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER=0;
    public static final int BANNER_AD_SLIDER=1;
    public static final int HORIZONTAL_PRODUCT_VIIEW=2;
    public static final int GRID_PRODUCT_VIIEW=3;
    private int type;
    private List<SliderModel> sliderModelList;

    private String backgroundcolor;
    private  String title;


    public HomePageModel(int type ,List<SliderModel> sliderModelList) {
        //this.backgroundcolor=backgroundcolor;
        this.type = type;
        //this.title=title;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////// strip add
    private  String resource;

    public HomePageModel(int type, String resource, String backgroundcolor) {
        this.type = type;
        this.resource = resource;
        this.backgroundcolor = backgroundcolor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    ///////// strip add

    ////////// HORIZONTAL PRODUCT LAYOUT
    //private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    private List<WishlistModel> viewProductlList;

    public HomePageModel(int type,String backgroundcolor ,String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<WishlistModel> viewAllActivityModelList) {
        this.viewProductlList=viewAllActivityModelList;
        this.backgroundcolor=backgroundcolor;
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public List<WishlistModel> getViewProductlList() {
        return viewProductlList;
    }

    public void setViewProductlList(List<WishlistModel> viewProductlList) {
        this.viewProductlList = viewProductlList;
    }

    public HomePageModel(int type, String backgroundcolor , String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.backgroundcolor=backgroundcolor;
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
}
