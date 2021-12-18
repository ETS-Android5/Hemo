package com.dev334.blood.model;

public class Blood {
    private String user;
    private Double latitude;
    private Double longitude;
    private String location;
    private Integer quantity;
    private String blood;

    Blood(){
        //empty constructor
    }

    public Blood(String user, Double latitude, Double longitude, String location, Integer quantity, String blood) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.quantity = quantity;
        this.blood = blood;
    }

    public String getUser() {
        return user;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getBlood() {
        return blood;
    }
}