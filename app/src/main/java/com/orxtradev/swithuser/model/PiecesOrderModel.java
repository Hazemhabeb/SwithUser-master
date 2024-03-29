package com.orxtradev.swithuser.model;

import java.io.Serializable;

public class PiecesOrderModel implements Serializable {



    private String id = "";
    private String name = "";
    private String price = "";
    private String quantity = "";


    public PiecesOrderModel() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
