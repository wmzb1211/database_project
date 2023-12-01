package com.EasyRide.entity;

public class Customer {
//    CREATE TABLE Customer (
//            customer_id INT PRIMARY KEY,
//            name VARCHAR(255),
//    contact_info VARCHAR(255),
//    license_number VARCHAR(20),
//    address VARCHAR(255)
//);
    private int id;
    private String name;
    private String contactInfo;
    private String licenseNumber;
    private String address;

    public Customer(int id, String name, String contactInfo, String licenseNumber, String address) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }

    public Customer(String name, String contactInfo, String licenseNumber, String address) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }

    public int getId() {
        return id;
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
