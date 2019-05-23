package com.example.jewharyimer.gebya;

public class AddressModel {
    private String fullname;
    private String mobile_no;
    private String address;
    private String pincode;
    private boolean selected;

    public AddressModel(String fullname, String address, String pincode,boolean selected,String mobile_no) {
        this.fullname = fullname;
        this.mobile_no=mobile_no;
        this.address = address;
        this.pincode = pincode;
        this.selected=selected;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
