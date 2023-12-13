package com.EasyRide.entity;

public class ViolationRecord {
    private int violationId;
    private int rentalId;
    private String violationType;
    private double fineAmount;

    private String description;
    public ViolationRecord(int violationId, int rentalId, String violationType, String violationDescription, double fineAmount) {
        this.violationId = violationId;
        this.rentalId = rentalId;
        this.violationType = violationType;
        this.fineAmount = fineAmount;
        this.description=violationDescription;
    }
    // Getters and setters

    public int getViolationId() {
        return violationId;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }


    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

}
