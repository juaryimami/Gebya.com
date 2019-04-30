package com.example.jewharyimer.gebya;

public class WishlistModel {
    private int productImage;
    private String productTitle;
    private int freeCoupen;
    private String rating;
    private int totalRating;
    private String productPrice;
    private String cuttedPrice;
    private String paymentMethod;

    public WishlistModel(int productImage, String productTitle,
                         int freeCoupen, String rating, int totalRating,
                         String productPrice, String cuttedPrice, String paymentMethod) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupen = freeCoupen;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.paymentMethod = paymentMethod;
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

    public int getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(int freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
