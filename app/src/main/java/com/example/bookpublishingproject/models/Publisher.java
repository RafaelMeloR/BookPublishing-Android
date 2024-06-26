package com.example.bookpublishingproject.models;

public class Publisher {
    private int p_id;
    private String p_name;
    private String p_address;

    public Publisher() {

    }

    public Publisher(int p_id, String p_name, String p_address) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_address = p_address;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }
}
