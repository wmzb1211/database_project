package com.EasyRide.entity;

import com.EasyRide.daoimpl.*;
import com.EasyRide.entity.RentalRecord;
import java.util.List;

public class Car {
    private int carId;
    private int modelId;
    private String plateNumber;
    private String color;
    private int year;
    private String status;
    private double dailyRentalFee;

    // Getters and setters

    public Car(int carId, int modelId, String plateNumber, String color, int year, String status, double dailyRentalFee) {
        this.carId = carId;
        this.modelId = modelId;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.status = status;
        this.dailyRentalFee = dailyRentalFee;
    }

    public int getCarId() {
        return carId;
    }


    public int getModelId() {
        return modelId;
    }


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public double getDailyRentalFee() {
        return dailyRentalFee;
    }

    public void setDailyRentalFee(double dailyRentalFee) {
        this.dailyRentalFee = dailyRentalFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        // 以JSON格式返回
        return "{\"carId\":" + carId +
                ",\"modelId\":" + modelId +
                ",\"plateNumber\":\"" + plateNumber +
                "\",\"color\":\"" + color +
                "\",\"year\":" + year +
                ",\"status\":\"" + status +
                "\",\"dailyRentalFee\":" + dailyRentalFee + "}";
    }

    public RentalRecord rent(int customerId, int duration) {
        RentalRecordDaoImpl rentalRecordDaoImpl = new RentalRecordDaoImpl();
        return rentalRecordDaoImpl.addRentalRecord(carId, customerId, duration);
    }

}
