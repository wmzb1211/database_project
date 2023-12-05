package com.EasyRide.entity;

import java.sql.Date;

public class Payment {
    private int paymentId;
    private int rentalId;
    private String paymentType;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;

    // Getters and setters

    public Payment(int paymentId, int rentalId, String paymentType, Date paymentDate, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}