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

    // Getters and setters
}