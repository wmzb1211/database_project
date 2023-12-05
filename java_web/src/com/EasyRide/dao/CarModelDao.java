package com.EasyRide.dao;

import com.EasyRide.entity.CarModel;

import java.util.List;

public interface CarModelDao {
    public CarModel getCarModel(String model);
    public CarModel addCarModel(String brand, String modelName, String description);
    public CarModel updateCarModel(CarModel carModel);
    public List<String> getAllBrands();

}
