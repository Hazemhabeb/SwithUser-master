package com.orxtradev.swithuser.model;

import java.util.ArrayList;

public class GovernmentModel {
    private String id = "";
    private String name  = "";
    private String shippingPrice = "";
    private ArrayList<DayModel> times =  new ArrayList<>();



    public GovernmentModel() {
    }

    public GovernmentModel(String ww) {
        times.add(new DayModel("Sat"));
        times.add(new DayModel("Sun"));
        times.add(new DayModel("Mon"));
        times.add(new DayModel("Tue"));
        times.add(new DayModel("Wed"));
        times.add(new DayModel("Thur"));
        times.add(new DayModel("Fri"));
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

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }


    public ArrayList<DayModel> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<DayModel> times) {
        this.times = times;
    }
}
