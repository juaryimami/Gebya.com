package com.example.jewharyimer.gebya;

public class MyOrderItemModel  {
    private int productImage;
    private String productTitle;
    private int ratting;
    private String deliveryStatus;

    public MyOrderItemModel(int productImage,int ratting ,String productTitle, String deliveryStatus) {
        this.productImage = productImage;
        this.ratting=ratting;
        this.productTitle = productTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }
}
