package com.EasyRide.entity;

public class Customer {
    private int customerId;
    private String name;
    private String account;
    private String password;
    private String contactInfo;
    private String licenseNumber;
    private String address;

    public Customer(int customerId, String name, String account, String password, String contactInfo, String licenseNumber, String address) {
        this.customerId = customerId;
        this.name = name;
        this.account = account;
        this.password = password;
        this.contactInfo = contactInfo;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }
    public Customer() {}

    public int getCustomerId() {
        return customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        // 输出JSON格式的字符串
        return "{\"customerId\":" + customerId +
                ",\"name\":\"" + name +
                "\",\"account\":\"" + account +
                "\",\"contactInfo\":\"" + contactInfo +
                "\",\"licenseNumber\":\"" + licenseNumber +
                "\",\"address\":\"" + address + "\"}";
    }

}