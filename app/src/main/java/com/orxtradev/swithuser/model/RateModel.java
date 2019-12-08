package com.orxtradev.swithuser.model;

public class RateModel {
    private String id = "";
    private String time = "";
    private String orderId = "";
    private String mandobId = "";
    private String message = "";
    private String rate  = "";
    private String userId = "";

    //1 for preview 2 for maintenance
    private String orderType = "";



    public RateModel() {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMandobId() {
        return mandobId;
    }

    public void setMandobId(String mandobId) {
        this.mandobId = mandobId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
