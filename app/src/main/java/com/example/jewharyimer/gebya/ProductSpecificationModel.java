package com.example.jewharyimer.gebya;

public class ProductSpecificationModel {
    public static final int SPECIFICATION_TITLE=0;
    public static final int SPECIFICATION_BODY=1;

    ///////// specification title
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ProductSpecificationModel(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    /////// specification title

    /////// specification body
    private String featureName;
    private String featurValue;

    public ProductSpecificationModel(int type, String featureName, String featurValue) {
        this.type = type;
        this.featureName = featureName;
        this.featurValue = featurValue;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeaturValue() {
        return featurValue;
    }

    public void setFeaturValue(String featurValue) {
        this.featurValue = featurValue;
    }
    /////// specification body


}
