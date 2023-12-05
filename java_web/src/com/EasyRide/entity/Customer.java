package com.EasyRide.entity;

public class Customer {
    private int customerId;
    private String name;
    private String contactInfo;
    private String licenseNumber;
    private String address;

    // Getters and setters

    public Customer(int customerId, String name, String contactInfo, String licenseNumber, String address) {
        this.customerId = customerId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}