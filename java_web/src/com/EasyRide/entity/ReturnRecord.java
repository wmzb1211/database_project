package com.EasyRide.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class ReturnRecord {
    private int returnId;
    private int rentalId;
    private int violationId;
    private Date returnDateTime;
    private String vehicleConditionDescription;
    private String fuelCondition;
    private String handlingPersonnel;

    public ReturnRecord(int returnId, int rentalId, int violationId, Date returnDateTime, String vehicleConditionDescription, String fuelCondition, String handlingPersonnel) {
        this.returnId = returnId;
        this.rentalId = rentalId;
        this.violationId = violationId;
        this.returnDateTime = returnDateTime;
        this.vehicleConditionDescription = vehicleConditionDescription;
        this.fuelCondition = fuelCondition;
        this.handlingPersonnel = handlingPersonnel;
    }
    // Getters and setters

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getViolationId() {
        return violationId;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
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

    public String getFuelCondition() {
        return fuelCondition;
    }

    public void setFuelCondition(String fuelCondition) {
        this.fuelCondition = fuelCondition;
    }

    public String getHandlingPersonnel() {
        return handlingPersonnel;
    }

    public void setHandlingPersonnel(String handlingPersonnel) {
        this.handlingPersonnel = handlingPersonnel;
    }
}