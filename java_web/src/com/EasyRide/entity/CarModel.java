package com.EasyRide.entity;

public class CarModel {
    private int modelId;
    private String brand;
    private String modelName;
    private String description;

    // Getters and setters

    public CarModel(int modelId, String brand, String modelName, String description) {
        this.modelId = modelId;
        this.brand = brand;
        this.modelName = modelName;
        this.description = description;
    }

    public CarModel() {

    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
    	// 以JSON格式输出
        return "{\"modelId\":" + modelId + ",\"brand\":\"" + brand + "\",\"modelName\":\"" + modelName + "\",\"description\":\"" + description + "\"}";
    }

}