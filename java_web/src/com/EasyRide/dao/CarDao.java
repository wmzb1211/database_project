package com.EasyRide.dao;

import com.EasyRide.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarDao {
    List<Car> getAllCars();
    Car getCarById(int id);
    List<Car> getCarsByStatus(String status);
    List<Car> getCarsByModelId(int modelId);
    List<Car> getCarsByFilter(Map<String, String> filterParams);
    Car addCar(int modelId, String plateNumber, String color, int year, String status, double dailyRentalFee);
    Car updateCar(Car car);
    boolean deleteCar(int id);
}
