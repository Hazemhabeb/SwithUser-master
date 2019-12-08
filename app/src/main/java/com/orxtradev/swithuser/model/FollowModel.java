package com.orxtradev.swithuser.model;

import java.util.ArrayList;

public class FollowModel {

    private String id = "";
    private String time = "";
    private String name = "";
    private String userId  = "";
    private String equipment =  "";
    private ArrayList<PiecesFollowModel> piecesFollow  = new ArrayList<>();


    public FollowModel() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public ArrayList<PiecesFollowModel> getPiecesFollow() {
        return piecesFollow;
    }

    public void setPiecesFollow(ArrayList<PiecesFollowModel> piecesFollow) {
        this.piecesFollow = piecesFollow;
    }
}
