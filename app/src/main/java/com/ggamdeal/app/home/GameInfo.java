package com.ggamdeal.app.home;

public class GameInfo {
    private String imageUrl;
    private String originalPrice;
    private String gameLink;
    private String discountPrice;
    private String title;
    private String discountRate;

    public GameInfo() {
    }

    public GameInfo(String imageUrl, String originalPrice, String gameLink, String discountPrice, String title, String discountRate) {
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.gameLink = gameLink;
        this.discountPrice = discountPrice;
        this.title = title;
        this.discountRate = discountRate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getGameLink() {
        return gameLink;
    }

    public void setGameLink(String gameLink) {
        this.gameLink = gameLink;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
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
