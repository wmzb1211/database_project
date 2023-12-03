package com.EasyRide.dao;

import com.EasyRide.entity.Car;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CarDaoImpl implements CarDao {

    /**
     * 返回所有车辆，包括已出租和未出租的
     */
    @Override
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
     */
    @Override
    public List<Car> getCarsByStatus(String status){
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

    /**
     * 返回某车型的所有车辆
     */
    @Override
    public List<Car> getCarsByModelId(int modelId) {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM car WHERE model_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, modelId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("car_id");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
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
    }

    /**
     * 返回符合筛选条件的所有车辆, Car表和CarModel表联合查询
     * 可以根据品牌(brand)、颜色(color)、年份(year)、状态(status)筛选
     * 同时可以根据日租金范围(minDailyRentalFee, maxDailyRentalFee)筛选
     * 注意：如果filterParams为null，返回所有车辆
     * 注意：参数参数为Map<String, String>，其中key为上述参数，value为参数值，value类型为String
     */
    @Override
    public List<Car> getUsersByFilter(Map<String, String> filterParams) {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            StringBuilder sb = new StringBuilder();
            if (filterParams != null){
                if (filterParams.containsKey("brand")){
                    sb.append(" AND model_id IN (SELECT model_id FROM car_model WHERE brand = ?)");
                }
                if (filterParams.containsKey("color")){
                    sb.append(" AND color = ?");
                }
                if (filterParams.containsKey("year")){
                    sb.append(" AND year = ?");
                }
                if (filterParams.containsKey("status")){
                    sb.append(" AND status = ?");
                }
                if (filterParams.containsKey("minDailyRentalFee")){
                    sb.append(" AND daily_rental_fee >= ?");
                }
                if (filterParams.containsKey("maxDailyRentalFee")){
                    sb.append(" AND daily_rental_fee <= ?");
                }
            }

            connection = DBConnectionPool.getConnection();
            // UNION联合查询
            String sql = "SELECT * INNER JOIN car_model ON car.model_id = car_model.model_id WHERE 1 = 1" + sb.toString();
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            if (filterParams != null){
                if (filterParams.containsKey("brand")){
                    preparedStatement.setString(index++, filterParams.get("brand"));
                }
                if (filterParams.containsKey("color")){
                    preparedStatement.setString(index++, filterParams.get("color"));
                }
                if (filterParams.containsKey("year")){
                    preparedStatement.setInt(index++, Integer.parseInt(filterParams.get("year")));
                }
                if (filterParams.containsKey("status")){
                    preparedStatement.setString(index++, filterParams.get("status"));
                }
                if (filterParams.containsKey("minDailyRentalFee")){
                    preparedStatement.setDouble(index++, Double.parseDouble(filterParams.get("minDailyRentalFee")));
                }
                if (filterParams.containsKey("maxDailyRentalFee")){
                    preparedStatement.setDouble(index++, Double.parseDouble(filterParams.get("maxDailyRentalFee")));
                }
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
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

    /**
     * 添加车辆
     */
    @Override
    public Car addCar(Car car) {
        return null;
    }

    @Override
    public Car updateCar(Car car) {
        return null;
    }

    @Override
    public boolean deleteCar(int id) {
        return false;
    }


}
