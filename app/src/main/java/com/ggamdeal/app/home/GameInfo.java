package com.ggamdeal.app.home;

public class GameInfo {
    private String imgUrl; //img
    private String title; //title
    private String link; //element link
    private String discountPrice; //discount_price
    private String originalPrice; //origianl_price
    private String discountRate; //discount_pct

    public GameInfo() {
    }

    public GameInfo(String imgUrl, String title, String link, String discountPrice, String originalPrice, String discountRate) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.link = link;
        this.discountPrice = discountPrice;
        this.originalPrice = originalPrice;
        this.discountRate = discountRate;
    }

    public GameInfo(String imgUrl, String title, String discountRate) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.discountRate = discountRate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
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
