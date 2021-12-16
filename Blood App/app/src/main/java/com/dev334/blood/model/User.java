package com.dev334.blood.model;

public class User {
    private String name;
    private String email;
    private String password;
    private Integer weight;
    private String gender;
    private String dob;
    private String blood;
    private String location;
    public User(){
        //empty
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email,Integer weight, String gender, String dob, String bloodGroup, String location) {
        this.email=email;
        this.weight = weight;
        this.gender = gender;
        this.dob = dob;
        this.blood = bloodGroup;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getBloodGroup() {
        return blood;
    }

    public String getLocation() {
        return location;
    }
}
