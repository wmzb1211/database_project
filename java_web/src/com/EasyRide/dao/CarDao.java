package com.EasyRide.dao;

import com.EasyRide.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarDao {
    List<Car> getAllCars();
    List<Car> getCarsByStatus(String status);
    List<Car> getCarsByModelId(int modelId);
    List<Car> getUsersByFilter(Map<String, String> filterParams);
    Car addCar(Car car);
    Car updateCar(Car car);
    boolean deleteCar(int id);
}
