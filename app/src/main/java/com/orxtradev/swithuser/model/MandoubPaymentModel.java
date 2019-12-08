package com.orxtradev.swithuser.model;

public class MandoubPaymentModel {
    private String id = "";
    //1 for not paid 2 for done paid
    private String status = "";
    private String amount = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
