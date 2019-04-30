package com.example.jewharyimer.gebya;

public class Category_Model {
    private String categoryIconLink;
    private String categoryName;

    public String getCategoryIconLink() {
        return categoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        this.categoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category_Model(String categoryIconLink, String categoryName) {

        this.categoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }
}
