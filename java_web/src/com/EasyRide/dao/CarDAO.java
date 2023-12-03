package com.EasyRide.dao;

public interface CarDAO {
    List<Car> getAllCars();
    List<Car> getCarsByStatus(String status);
    List<Car> getCars
    Car getCarById(int id);
    Car addCar(Car car);
    Car updateCar(Car car);
    boolean deleteCar(int id);
}
