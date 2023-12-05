package com.EasyRide.entity;

import java.sql.Date;

public class Administrator {
    private int adminId;
    private String name;
    private String username;
    private String password;
    private String contactInfo;
    private String role;
    private Date creationDate;

    public Administrator(int adminId, String name, String username, String password, String contactInfo, String role, Date creationDate) {
        this.adminId = adminId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactInfo = contactInfo;
        this.role = role;
        this.creationDate = creationDate;
    }
// Getters and setters

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}