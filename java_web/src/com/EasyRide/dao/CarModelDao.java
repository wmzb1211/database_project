package com.EasyRide.dao;

import com.EasyRide.entity.CarModel;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CarModelDao {

    public CarModel getCarModelById(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CarModel carModel = null;

        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM carmodel WHERE model_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                carModel = new CarModel();
                carModel.setModelId(resultSet.getInt("model_id"));
                carModel.setBrand(resultSet.getString("brand"));
                carModel.setModelName(resultSet.getString("model_name"));
                carModel.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
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
        return carModel;
    }

    public List<CarModel> getAllCarModels(){
        List<CarModel> carModels = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM carmodel");
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                CarModel carModel = new CarModel();
                carModel.setModelId(resultSet.getInt("model_id"));
                carModel.setBrand(resultSet.getString("brand"));
                carModel.setModelName(resultSet.getString("model_name"));
                carModel.setDescription(resultSet.getString("description"));
                carModels.add(carModel);
            }
        } catch (SQLException e) {
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
        return carModels;
    }
    public CarModel addCarModel(String brand, String modelName, String description){
        CarModel carModel = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO carmodel (brand, model_name, description) " +
                    "VALUES (?, ?, ?)");
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, modelName);
            preparedStatement.setString(3, description);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car model failed, no rows affected.");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                carModel = new CarModel();
                carModel.setModelId(resultSet.getInt(1));
                carModel.setBrand(brand);
                carModel.setModelName(modelName);
                carModel.setDescription(description);
            } else {
                throw new SQLException("Creating car model failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }
    public CarModel updateCarModel(CarModel carModel){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE carmodel SET brand = ?, model_name = ?, description = ? WHERE model_id = ?");
            preparedStatement.setString(1, carModel.getBrand());
            preparedStatement.setString(2, carModel.getModelName());
            preparedStatement.setString(3, carModel.getDescription());
            preparedStatement.setInt(4, carModel.getModelId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }
    public List<String> getAllBrands(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> brands = new ArrayList<>();
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT DISTINCT brand FROM carmodel";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                brands.add(resultSet.getString("brand"));
            }
        } catch (SQLException e) {
        }
        return brands;
    }

    // 测试代码
    public static void main(String[] args) {
        CarModelDao carModelDao = new CarModelDao();
        CarModel carModel = carModelDao.getCarModelById(80);
        System.out.printf("model_id: %d, brand: %s, model_name: %s, description: %s\n",
                carModel.getModelId(), carModel.getBrand(), carModel.getModelName(), carModel.getDescription());
    }
}
