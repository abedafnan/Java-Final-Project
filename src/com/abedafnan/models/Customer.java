package com.abedafnan.models;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private int mobile;
    private String address;
    private String email;
    private String gender;

    public Customer(int id, String firstName, String lastName, int mobile, String address, String email, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return id + " - " + firstName + " " + lastName;
    }
}
