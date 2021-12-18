package com.dev334.blood.model;

public class BloodReq {
    private String location, blood;

    public BloodReq(String location, String blood) {
        this.location = location;
        this.blood = blood;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
