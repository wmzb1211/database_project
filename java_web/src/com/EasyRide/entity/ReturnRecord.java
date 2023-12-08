package com.EasyRide.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class ReturnRecord {
    private int returnId;
    private int rentalId;
    private Date returnDateTime;
    private String vehicleConditionDescription;
    private int adminId;

    // Getters and setters
    public ReturnRecord(int returnId, int rentalId, Date returnDateTime, String vehicleConditionDescription, int adminId) {
        this.returnId = returnId;
        this.rentalId = rentalId;
        this.returnDateTime = returnDateTime;
        this.vehicleConditionDescription = vehicleConditionDescription;
        this.adminId = adminId;
    }

    public int getReturnId() {
        return returnId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public Date getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(Date returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public String getVehicleConditionDescription() {
        return vehicleConditionDescription;
    }

    public void setVehicleConditionDescription(String vehicleConditionDescription) {
        this.vehicleConditionDescription = vehicleConditionDescription;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}