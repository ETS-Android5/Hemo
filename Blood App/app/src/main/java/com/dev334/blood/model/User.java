package com.dev334.blood.model;

public class User {
    private String name;
    private String email;
    private String password;
    private String weight;
    private String gender;
    private String dob;
    private String bloodGroup;
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

    public User(String weight, String gender, String dob, String bloodGroup, String location) {
        this.weight = weight;
        this.gender = gender;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
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

    public String getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLocation() {
        return location;
    }
}
