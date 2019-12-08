package com.orxtradev.swithuser.model;

public class equimentPieces {
    private String id = "";
    private String time = "";
    private String price  = "";
    private String buyPrice = "";
    private String sellingPrice = "";
    private String equimentId  = "";
    private String name  = "";
    private String image = "";
    private String numberOfDays  = "";



    public equimentPieces() {
    }


    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEquimentId() {
        return equimentId;
    }

    public void setEquimentId(String equimentId) {
        this.equimentId = equimentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getNumberOfDays() {

        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
