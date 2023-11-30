package com.ggamdeal.app.home;

public class GameInfo {
    private String imgUrl; //img
    private String title; //title
    private String discountRate; //discount_pct

    public GameInfo() {
    }

    public GameInfo(String imgUrl, String title, String discountRate) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.discountRate = discountRate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }
}
