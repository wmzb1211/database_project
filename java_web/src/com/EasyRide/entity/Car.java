package com.EasyRide.entity;


//CREATE TABLE Car (
//    car_id INT PRIMARY KEY,
//    model_id INT,
//    plate_number VARCHAR(20),
//color VARCHAR(50),
//year INT,
//status VARCHAR(50),
//daily_rental_fee DECIMAL(10, 2),
//FOREIGN KEY (model_id) REFERENCES CarModel(model_id)
//    );

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
}
