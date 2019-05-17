package com.example.jewharyimer.gebya;

public class CartItemModel {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;

    private int TYPE;

    public CartItemModel(int totalAmount) {
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }
    //////////// CART ITEM

    public CartItemModel(int TYPE, String prouductImage, String productTitle, Long freeCoupens, String productPrice,
                         String cuttedPrice, int productQuantity, Long offersApplied, Long coupensApplied) {
        this.TYPE = TYPE;
        this.prouductImage = prouductImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
    }

    public String getProuductImage() {
        return prouductImage;
    }

    public void setProuductImage(String prouductImage) {
        this.prouductImage = prouductImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(long freeCoupens) {
        this.freeCoupens = freeCoupens;
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

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(int coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    private String prouductImage;
    private String productTitle;
    private String productID;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private long productQuantity;
    private long offersApplied;
    private long coupensApplied;

    public static int getTotalAmount() {
        return TOTAL_AMOUNT;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    //////////// CART ITEM
    ///////////// CART TOTAL



    ///////////// CART TOTAL
}
