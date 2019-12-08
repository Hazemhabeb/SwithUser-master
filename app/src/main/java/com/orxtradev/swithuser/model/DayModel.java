package com.orxtradev.swithuser.model;

public class DayModel {


    private String day  = "";
    private String available  = "";
    private String startHour = "";
    private String endHour  = "";


    public DayModel() {

    }
    public DayModel(String day) {
        this.day = day;
        this.available = "0";
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }
}
