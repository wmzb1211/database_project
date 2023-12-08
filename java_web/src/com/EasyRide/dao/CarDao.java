package com.EasyRide.dao;

import com.EasyRide.entity.Car;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CarDao {

    /**
     * 返回所有车辆，包括已出租和未出租的
     */
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionPool.getConnection();
//            String sql = "SELECT * FROM car";
            String sql =  "SELECT * FROM Car c"
                    + " INNER JOIN CarModel cm ON c.model_id = cm.model_id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("car_id");
                int modelId = resultSet.getInt("model_id");
                String brand = resultSet.getString("brand");
                String modelName = resultSet.getString("model_name");
                String description = resultSet.getString("description");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                Car car = new Car(id, modelId, brand, modelName, description, plateNumber, color, year, status, dailyRentalFee);
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


    public Car getCarById(int id) {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car car = null;
        try{
            connection = DBConnectionPool.getConnection();
//            String sql = "SELECT * FROM car WHERE car_id = ?";
            String sql =  "SELECT * FROM Car c"
                    + " INNER JOIN CarModel cm ON c.model_id = cm.model_id"
                    + " WHERE c.car_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int modelId = resultSet.getInt("model_id");
                String brand = resultSet.getString("brand");
                String modelName = resultSet.getString("model_name");
                String description = resultSet.getString("description");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                car = new Car(id, modelId, brand, modelName, description, plateNumber, color, year, status, dailyRentalFee);
                return car;
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
        return car;
    }

    /**
     * 返回某状态的所有车辆
     */

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
                int carId = resultSet.getInt("car_id");
                int modelId = resultSet.getInt("model_id");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                Car car = new Car(carId, modelId, plateNumber, color, year, status, dailyRentalFee);
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
            e.printStackTrace()
            ;
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
     * 返回符合筛选条件的所有车辆, Car表和CarModel表联合查询
     * 可以根据品牌(brand)、颜色(color)、年份(year)、状态(status)筛选
     * 同时可以根据日租金范围(minDailyRentalFee, maxDailyRentalFee)筛选
     * 注意：如果filterParams为null，返回所有车辆
     * 注意：参数参数为Map<String, String>，其中key为上述参数，value为参数值，value类型为String
     */
    public List<Car> getCarsByFilter(Map<String, String> filterParams) {
        if (filterParams == null || filterParams.isEmpty()){
            return getAllCars();
        }

        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            StringBuilder sb = getStringBuilder(filterParams);
            String sql =  "SELECT * FROM Car c"
                    + " INNER JOIN CarModel cm ON c.model_id = cm.model_id"
                    + " WHERE 1=1 "
                    + sb;
            System.out.println(sql);
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            if (filterParams != null){
                if (filterParams.containsKey("brand")){
                    preparedStatement.setString(index++, filterParams.get("brand"));
                }
                if (filterParams.containsKey("model")){
                    preparedStatement.setString(index++, filterParams.get("model"));
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
                    preparedStatement.setDouble(index, Double.parseDouble(filterParams.get("maxDailyRentalFee")));
                }
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("car_id");
                int modelId = resultSet.getInt("model_id");
                String brand = resultSet.getString("brand");
                String modelName = resultSet.getString("model_name");
                String description = resultSet.getString("description");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                Car car = new Car(id, modelId, brand, modelName, description, plateNumber, color, year, status, dailyRentalFee);
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

    private static StringBuilder getStringBuilder(Map<String, String> filterParams) {
        StringBuilder sb = new StringBuilder();
        if (filterParams != null){
            if (filterParams.containsKey("brand")){
                sb.append(" AND cm.brand = ?");
            }
            if (filterParams.containsKey("model")){
                sb.append(" AND cm.model_name =?");
            }
            if (filterParams.containsKey("color")){
                sb.append(" AND c.color = ?");
            }
            if (filterParams.containsKey("year")){
                sb.append(" AND c.year = ?");
            }
            if (filterParams.containsKey("status")){
                sb.append(" AND c.status = ?");
            }
            if (filterParams.containsKey("minDailyRentalFee")){
                sb.append(" AND c.daily_rental_fee >= ?");
            }
            if (filterParams.containsKey("maxDailyRentalFee")){
                sb.append(" AND c.daily_rental_fee <= ?");
            }
        }
        return sb;
    }

    /**
     * 添加车辆
     */
    public Car addCar(Car car) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car newCar = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "INSERT INTO car(model_id, plate_number, color, year, status, daily_rental_fee) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, car.getModelId());
            preparedStatement.setString(2, car.getPlateNumber());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setString(5, car.getStatus());
            preparedStatement.setDouble(6, car.getDailyRentalFee());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("Creating car failed, no rows affected.");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                newCar = new Car(resultSet.getInt(1), car.getModelId(), car.getPlateNumber(), car.getColor(),
                        car.getYear(), car.getStatus(), car.getDailyRentalFee());
            } else {
                throw new SQLException("Creating car failed, no ID obtained.");
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
        return newCar;
    }

    /**
     * 更新车辆信息
     * 注意：Status只能为Available、Rented、Maintenance、Unusable四种之一
     * 注意：car的id必须存在，不允许更新id，可以更新其他信息
     */
    public Car updateCar(Car car) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car updatedCar = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "UPDATE car SET model_id = ?, plate_number = ?, color = ?, year = ?, status = ?, daily_rental_fee = ? WHERE car_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, car.getModelId());
            preparedStatement.setString(2, car.getPlateNumber());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setString(5, car.getStatus());
            preparedStatement.setDouble(6, car.getDailyRentalFee());
            preparedStatement.setInt(7, car.getCarId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Updating car failed, no rows affected.");
            }
            updatedCar = car;
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
        return updatedCar;
    }


    public boolean deleteCar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "DELETE FROM car WHERE car_id = ?";
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

    public List<String> getAllFilterOptions(String filter){
        List<String> options = new ArrayList<>();
        List<String> availableParameters = Arrays.asList("color", "year", "status");

        if (!availableParameters.contains(filter)){
            return options;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT DISTINCT " + filter + " FROM car";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                options.add(resultSet.getString(1));
            }
        } catch (SQLException e){
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
        return options;
    }

    // 测试代码
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        // 测试getAllCars()
        System.out.println("=== Test getAllCars() ===");
        List<Car> cars = carDao.getAllCars();
        for (Car car : cars){
            System.out.println(car);
        }

        // 测试getCarsByStatus()
        System.out.println("=== Test getCarsByStatus() ===");
        cars = carDao.getCarsByStatus("Rented");
        for (Car car : cars){
            System.out.println(car);
        }

        // 测试getCarsByModelId()
        System.out.println("=== Test getCarsByModelId() ===");
        cars = carDao.getCarsByModelId(1);
        for (Car car : cars){
            System.out.println(car);
        }

        // 测试getUsersByFilter()
        System.out.println("=== Test getUsersByFilter() ===");
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("brand", "Toyota");
        filterParams.put("color", "DarkOliveGreen");
        filterParams.put("year", "2008");
        filterParams.put("status", "Available");
        filterParams.put("minDailyRentalFee", "100");
        filterParams.put("maxDailyRentalFee", "200");
        cars = carDao.getCarsByFilter(filterParams);
        for (Car car : cars){
            System.out.println(car);
        }

        // 测试addCar()
        System.out.println("=== Test addCar() ===");
        Car car = carDao.addCar(new Car(0, 1, "粤A12345", "red", 2018, "available", 100));
        System.out.println(car);

        car.setColor("blue");
        // 测试updateCar()
        System.out.println("=== Test updateCar() ===");
        car = carDao.updateCar(car);
        System.out.println(car);

        // 测试deleteCar()
        System.out.println("=== Test deleteCar() ===");
        boolean result = carDao.deleteCar(car.getCarId());
        System.out.println(result);
    }
}
