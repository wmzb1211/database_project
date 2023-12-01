package com.EasyRide.dao;

import com.EasyRide.entity.Car;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CarDAO {
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM car";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("car_id");
                int modelId = resultSet.getInt("model_id");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                Car car = new Car(id, modelId, plateNumber, color, year, status, dailyRentalFee);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cars;
    }

    /**
     * 返回某状态的所有车辆
     * @param status
     * @return List<Car>
     */
    public List<Car> getAllCarsByStatus(String status){
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM car WHERE status = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("car_id");
                int modelId = resultSet.getInt("model_id");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                Car car = new Car(id, modelId, plateNumber, color, year, status, dailyRentalFee);
                cars.add(car);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return cars;

    }
}
