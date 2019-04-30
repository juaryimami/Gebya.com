package com.example.jewharyimer.gebya;

public class HorizontalProductScrollModel {
    private int ProductImage;
    private String  ProductTitle;
    private String ProductDescription;
    private String ProductPrice;


    public HorizontalProductScrollModel(int productImage, String productTitle, String productDescription, String productPrice) {
        ProductImage = productImage;
        ProductTitle = productTitle;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
    }

    public int getProductImage() {
        return ProductImage;
    }

    public void setProductImage(int productImage) {
        ProductImage = productImage;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }
}
