package com.orxtradev.swithuser.model;

import java.io.Serializable;
import java.util.ArrayList;

public class maintenanceOrder implements Serializable {

    private String id   = "";
    private String name = "";
    private String phone  = "";
    private String orderNumber = "";
    private String phone2 = "";
    private String government  = "";
    private String address = "";
    private String lat = "";
    private String lang = "";
    private String buildingNumber = "";
    private String flatNumber  = "" ;
    private String landmark = "";
    private String equimentId = "";
    private String userId = "";
    private String time = "";
    private String fromClock = "";
    private String toClock ="";
    private String pieceId = "";
    private String pieceNumber = "";
    private String note  = "";
    private String day = "";
    private String totalPrice  = "";
    private String coponId  = "";
    //0 for pending 1 for accpeted 2 for workinng 3 for finishedd -1 for refused
    private String status = "";
    private String mandoubId = "";
    private String discount  = "";
    private String shippingPrice = "";



    private ArrayList<PiecesOrderModel> piecesOrder =  new ArrayList<>();


    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public ArrayList<PiecesOrderModel> getPiecesOrder() {
        return piecesOrder;
    }

    public void setPiecesOrder(ArrayList<PiecesOrderModel> piecesOrder) {
        this.piecesOrder = piecesOrder;
    }

    public String getMandoubId() {
        return mandoubId;
    }

    public void setMandoubId(String mandoubId) {
        this.mandoubId = mandoubId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCoponId() {
        return coponId;
    }

    public void setCoponId(String coponId) {
        this.coponId = coponId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getEquimentId() {
        return equimentId;
    }

    public void setEquimentId(String equimentId) {
        this.equimentId = equimentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFromClock() {
        return fromClock;
    }

    public void setFromClock(String fromClock) {
        this.fromClock = fromClock;
    }

    public String getToClock() {
        return toClock;
    }

    public void setToClock(String toClock) {
        this.toClock = toClock;
    }

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(String pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
