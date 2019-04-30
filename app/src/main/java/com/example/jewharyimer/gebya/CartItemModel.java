package com.example.jewharyimer.gebya;

public class CartItemModel {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;

    private int TYPE;

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }
    //////////// CART ITEM

    public CartItemModel(int TYPE, int prouductImage, String productTitle, int freeCoupens, String productPrice,
                         String cuttedPrice, int productQuantity, int offersApplied, int coupensApplied) {
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

    public int getProuductImage() {
        return prouductImage;
    }

    public void setProuductImage(int prouductImage) {
        this.prouductImage = prouductImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(int freeCoupens) {
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public int getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(int coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    private int prouductImage;
    private String productTitle;
    private int freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private int productQuantity;
    private int offersApplied;
    private int coupensApplied;

    //////////// CART ITEM
    ///////////// CART TOTAL

    private String totalItems;
    private String totalItemPrice;
    private String totalAmount;
    private String deliveryPrice;
    private String savedAmount;

    public CartItemModel(int TYPE, String totalItems, String totalItemPrice,String totalAmount, String deliveryPrice, String savedAmount) {
        this.TYPE = TYPE;
        this.totalAmount=totalAmount;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
    }


    public static int getCartItem() {
        return CART_ITEM;
    }

    public static int getTotalAmount() {
        return TOTAL_AMOUNT;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getTotalAsmount(){
        return totalAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    //public String setTotal
    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }
    ///////////// CART TOTAL
}
