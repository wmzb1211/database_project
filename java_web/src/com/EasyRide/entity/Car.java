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
    private int id;
    private int modelId;
    private String plateNumber;
    private String color;
    private int year;
    private String status;
    private double dailyRentalFee;


    public Car(int id, int modelId, String plateNumber, String color, int year, String status, double dailyRentalFee) {
        this.id = id;
        this.modelId = modelId;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.status = status;
        this.dailyRentalFee = dailyRentalFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDailyRentalFee() {
        return dailyRentalFee;
    }

    public void setDailyRentalFee(double dailyRentalFee) {
        this.dailyRentalFee = dailyRentalFee;
    }

    @Override
    public String toString() {
        // 输出为JSON格式
        return "{" +
                "\"id\":" + id +
                ", \"modelId\":" + modelId +
                ", \"plateNumber\":\"" + plateNumber + '\"' +
                ", \"color\":\"" + color + '\"' +
                ", \"year\":" + year +
                ", \"status\":\"" + status + '\"' +
                ", \"dailyRentalFee\":" + dailyRentalFee +
                '}';
    }

    // 获得所有车辆信息
    public static void getAllCars() {
        CarDAO carDAO = new CarDAO();
        // 调用CarDAO的getAllCars方法
        List<Car> cars = carDAO.getAllCars();
        // 遍历cars集合
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    // 测试函数
    public static void main(String[] args) {
        getAllCars();
    }
}
