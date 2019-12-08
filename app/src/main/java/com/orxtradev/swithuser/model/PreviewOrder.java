package com.orxtradev.swithuser.model;

import java.io.Serializable;

public class PreviewOrder implements Serializable {

    private String id   = "";
    private String name = "";
    private String orderNumber = "";
    private String phone  = "";
    private String phone2 = "";
    private String government  = "";
    private String address = "";
    private String lat = "";
    private String lang = "";
    private String buildingNumber = "";
    private String flatNumber  = "" ;
    private String landmark = "";
    private String equimentTypeId = "";
    private String userId = "";
    private String time = "";
    //0 for pending 1 for accpeted 2 for workinng 3 for finishedd -1 for refused
    private String status = "";
    private String mandoubId = "";

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


    public PreviewOrder() {
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getEquimentTypeId() {
        return equimentTypeId;
    }

    public void setEquimentTypeId(String equimentTypeId) {
        this.equimentTypeId = equimentTypeId;
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
}


