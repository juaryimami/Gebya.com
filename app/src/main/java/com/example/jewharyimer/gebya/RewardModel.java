package com.example.jewharyimer.gebya;

public class RewardModel {
    private String title;
    private String expirdate;
    private String coupenbody;

    public RewardModel(String title, String expirdate, String coupenbody) {
        this.title = title;
        this.expirdate = expirdate;
        this.coupenbody = coupenbody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpirdate() {
        return expirdate;
    }

    public void setExpirdate(String expirdate) {
        this.expirdate = expirdate;
    }

    public String getCoupenbody() {
        return coupenbody;
    }

    public void setCoupenbody(String coupenbody) {
        this.coupenbody = coupenbody;
    }
}
