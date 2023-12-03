package com.EasyRide.entity;
import com.EasyRide.dao.CarDAO;
import javax.xml.crypto.Data;
import java.util.List;


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
}
