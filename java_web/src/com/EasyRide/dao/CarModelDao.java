package com.EasyRide.dao;

import com.EasyRide.entity.BrandStats;
import com.EasyRide.entity.CarModel;
import com.EasyRide.util.DBConnectionPool;

import java.math.BigDecimal;
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

    public CarModel getCarModelByBrandAndModelName(String brand, String modelName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CarModel carModel = null;

        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM carmodel WHERE brand =? AND model_name =?");
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, modelName);
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
                    "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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
        }finally {
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
        }finally {
            try{
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e){
                e.printStackTrace();
            }
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
            e.printStackTrace();
        } finally {
            try{
                if (resultSet!= null) resultSet.close();
                if (preparedStatement!= null) preparedStatement.close();
                if (connection!= null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return brands;
    }
    public List<String> getAllModelNames(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> modelNames = new ArrayList<>();
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT DISTINCT model_name FROM carmodel";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                modelNames.add(resultSet.getString("modelName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (resultSet!= null) resultSet.close();
                if (preparedStatement!= null) preparedStatement.close();
                if (connection!= null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return modelNames;
    }
    public List<CarModel> getModelsByBrand(String brand) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CarModel> models = new ArrayList<>();

        try{
            if (brand!= null &&!brand.equals("")) {
                connection = DBConnectionPool.getConnection();
                String sql = "SELECT * FROM carmodel WHERE brand =?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, brand);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt("model_id");
                    String modelName = resultSet.getString("model_name");
                    String description = resultSet.getString("description");
                    models.add(new CarModel(id, brand, modelName, description));
                }
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
        return models;
    }

    public List<BrandStats> getBrandStats (){
        List<BrandStats> statsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT cm.brand, COUNT(rr.rental_id) AS rental_count, " +
                    "SUM(DATEDIFF(IFNULL(rr.actual_return_date, CURDATE()), rr.start_date)) AS total_rental_days, " +
                    "SUM(rr.rental_fee) AS total_income " +
                    "FROM CarModel cm " +
                    "JOIN Car c ON cm.model_id = c.model_id " +
                    "JOIN RentalRecord rr ON c.car_id = rr.car_id " +
                    "GROUP BY cm.brand";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BrandStats stats = new BrandStats();
                stats.setBrand(resultSet.getString("brand"));
                stats.setRentalCount(resultSet.getInt("rental_count"));
                stats.setTotalRentalDays(resultSet.getInt("total_rental_days"));
                stats.setTotalIncome(BigDecimal.valueOf(resultSet.getDouble("total_income")));
                statsList.add(stats);
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

        return statsList;
    }
    public List<CarModel> getModelsByModelName(String modelName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CarModel> models = new ArrayList<>();

        try{
            if (modelName!= null &&!modelName.equals("")) {
                connection = DBConnectionPool.getConnection();
                String sql = "SELECT * FROM carmodel WHERE model_name =?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, modelName);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt("model_id");
                    String brand = resultSet.getString("brand");
                    String description = resultSet.getString("description");
                    models.add(new CarModel(id, brand, modelName, description));
                }
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
        return models;
    }
    public boolean deleteRentalRecord(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "DELETE FROM rentalrecord WHERE rental_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                return true;
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
        return false;
    }
    public boolean deleteCarModel(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "DELETE FROM carmodel WHERE model_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                return true;
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
        return false;
    }
    // 测试代码
    public static void main(String[] args) {
        CarModelDao carModelDao = new CarModelDao();
        CarModel carModel = carModelDao.getCarModelById(80);
        System.out.printf("model_id: %d, brand: %s, model_name: %s, description: %s\n",
                carModel.getModelId(), carModel.getBrand(), carModel.getModelName(), carModel.getDescription());

        System.out.println("=== Test getAllBrands() ===");
        List<String> brands = carModelDao.getAllBrands();
        for (String brand : brands) {
            System.out.println(brand);
        }

        System.out.println("=== Test getModelsByBrand() ===");
        List<CarModel> models = carModelDao.getModelsByBrand("Toyota");
        for (CarModel model : models) {
            System.out.println(model);
        }
    }
}
