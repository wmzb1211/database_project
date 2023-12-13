package com.EasyRide.entity;

import java.sql.Date;

public class RentalRecord {
    private int rentalId;
    private int customerId;
    private int carId;
    private Date startDate;
    private Date expectedReturnDate;
    private Date actualReturnDate;
    private double rentalFee;
    private String status;

    public RentalRecord(int rentalId, int customerId, int carId, Date startDate, Date expectedReturnDate, Date actualReturnDate, double rentalFee, String status) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.rentalFee = rentalFee;
        this.status = status;
    }

    public RentalRecord() {
    }

    // Getters and setters

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        // Json format
        return "{" +
                "\"rentalId\":" + rentalId +
                ", \"customerId\":" + customerId +
                ", \"carId\":" + carId +
                ", \"startDate\":\"" + startDate + "\"" +
                ", \"expectedReturnDate\":\"" + expectedReturnDate + "\"" +
                ", \"actualReturnDate\":\"" + actualReturnDate + "\"" +
                ", \"rentalFee\":" + rentalFee +
                ", \"status\":\"" + status + "\"" +
                "}";
    }
}