package com.orxtradev.swithuser.model;

import java.io.Serializable;

public class PiecesFollowModel implements Serializable {



    private String id = "";
    private String name = "";
    private String changeTime = "";


    public PiecesFollowModel() {
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


    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
