package com.example.jewharyimer.gebya;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER=0;
    public static final int BANNER_AD_SLIDER=1;
    public static final int HORIZONTAL_PRODUCT_VIIEW=2;
    public static final int GRID_PRODUCT_VIIEW=3;
    private int type;
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
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
    private  int resource;
    private String backgroundcolor;

    public HomePageModel(int type, int resource, String backgroundcolor) {
        this.type = type;
        this.resource = resource;
        this.backgroundcolor = backgroundcolor;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
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
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HomePageModel(int type, String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
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
