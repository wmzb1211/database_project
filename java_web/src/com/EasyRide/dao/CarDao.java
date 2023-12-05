package com.EasyRide.dao;

import com.EasyRide.entity.Car;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CarDao {

    /**
     * 返回所有车辆
     * 注意：如果数据库中没有车辆，返回空的List<Car>
     * @return List<Car>
     */
    
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
     * 返回某id的车辆，用于查看某车辆的详情信息
     * @param id 车辆id
     * @return Car对象，如果id不存在，返回null
     */
    public Car getCarById(int id) {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car car = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM car WHERE car_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int modelId = resultSet.getInt("model_id");
                String plateNumber = resultSet.getString("plate_number");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                String status = resultSet.getString("status");
                double dailyRentalFee = resultSet.getDouble("daily_rental_fee");
                car = new Car(id, modelId, plateNumber, color, year, status, dailyRentalFee);
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
     * @param status 车辆状态，取值为"Available", "Rented", "Under Repair", "Discarded" 等等
     * @return List<Car>
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
     * @param modelId 车型id
     * @return List<Car>
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
     * @param filterParams 筛选条件
     *                     key为上述参数，value为参数值，value类型为String
     * @return List<Car>
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
            String sql =  "SELECT c.* FROM Car c"
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

    private static StringBuilder getStringBuilder(Map<String, String> filterParams) {
        StringBuilder sb = new StringBuilder();
        if (filterParams != null){
            if (filterParams.containsKey("brand")){
                sb.append(" AND cm.brand = ?");
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
     * @param car Car对象，注意carId不需要设置或者设置为0
     * @return 添加成功返回Car对象，其中包含数据库自动生成的carId，添加失败返回null
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
     * 注意：car的id必须存在，不允许更新id，可以更新其他信息
     * @param car Car对象，其中carId必须存在，不能修改
     *            可以更新modelId, plateNumber, color, year, status, dailyRentalFee
     * @return 更新成功返回Car对象，否则返回null
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

    /**
     * 删除车辆
     * @param id 车辆id
     * @return 删除成功返回true，否则返回false
     */
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
        Car car = new Car(0, 1, "test", "test", 2000, "Available", 100);
        car = carDao.addCar(car);
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
