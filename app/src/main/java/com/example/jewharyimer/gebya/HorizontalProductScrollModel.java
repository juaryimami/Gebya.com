package com.example.jewharyimer.gebya;

public class HorizontalProductScrollModel {

    private String productID;
    private String ProductImage;
    private String  ProductTitle;
    private String ProductDescription;
    private String ProductPrice;


    public HorizontalProductScrollModel(String productID,String productImage, String productTitle, String productDescription, String productPrice) {
        this.productID=productID;
        this.ProductImage = productImage;
        this.ProductTitle = productTitle;
        this.ProductDescription = productDescription;
        this.ProductPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
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
