package com.EasyRide.dao;

import com.EasyRide.entity.Car;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.RentalRecord;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RentalRecordDao {
    public RentalRecord getRentalRecordsByID(int id){
        RentalRecord rentalRecord = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM rentalrecord WHERE rental_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                rentalRecord = new RentalRecord(
                        resultSet.getInt("rental_id"), resultSet.getInt("customer_id"),
                        resultSet.getInt("car_id"), resultSet.getDate("start_date"),
                        resultSet.getDate("expected_return_date"), resultSet.getDate("actual_return_date"),
                        resultSet.getDouble("rental_fee"), resultSet.getString("status"));
            }

        } catch (Exception e){
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
        return rentalRecord;
    }

    // status为null时，返回所有状态的rental record
    public List<RentalRecord> getRentalRecordsByCustomerID(int customerId, String status) {
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM rentalrecord WHERE customer_id = ?";
            if (status != null) {
                sql += " AND status = ?";
            }
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            if (status != null) {
                preparedStatement.setString(2, status);
            }
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int rentalId = resultSet.getInt("rental_id");
                int carId = resultSet.getInt("car_id");
                Date startDate = resultSet.getDate("start_date");
                Date expectedReturnDate = resultSet.getDate("expected_return_date");
                Date actualReturnDate = resultSet.getDate("actual_return_date");
                double rentalFee = resultSet.getDouble("rental_fee");
                String status1 = resultSet.getString("status");
                RentalRecord rentalRecord1 = new RentalRecord(rentalId, customerId, carId, startDate, expectedReturnDate, actualReturnDate, rentalFee, status1);
                rentalRecords.add(rentalRecord1);
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
        return rentalRecords;
    }

    public List<RentalRecord> getRentalRecordsByCarID(int carId) {
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;
        try {
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM rentalrecord WHERE car_id = ?");
            preparedStatement.setInt(1, carId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int rentalId = resultSet.getInt("rental_id");
                int customerId = resultSet.getInt("customer_id");
                Date startDate = resultSet.getDate("start_date");
                Date expectedReturnDate = resultSet.getDate("expected_return_date");
                Date actualReturnDate = resultSet.getDate("actual_return_date");
                double rentalFee = resultSet.getDouble("rental_fee");
                String status = resultSet.getString("status");
                RentalRecord rentalRecord1 = new RentalRecord(rentalId, customerId, carId, startDate, expectedReturnDate, actualReturnDate, rentalFee, status);
                rentalRecords.add(rentalRecord1);
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
        return rentalRecords;
    }

    public List<RentalRecord> getRentalRecordByFilter(Map<String, String> filterParams) {
        if (filterParams == null || filterParams.isEmpty()) {
            return getAllRentalRecords();
        }
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            StringBuilder sb = getStringBuilder(filterParams);
            String sql = "SELECT * FROM rentalrecord WHERE 1=1 " + sb.toString();
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int rentalId = resultSet.getInt("rental_id");
                int customerId = resultSet.getInt("customer_id");
                int carId = resultSet.getInt("car_id");
                Date startDate = resultSet.getDate("start_date");
                Date expectedReturnDate = resultSet.getDate("expected_return_date");
                Date actualReturnDate = resultSet.getDate("actual_return_date");
                double rentalFee = resultSet.getDouble("rental_fee");
                String status = resultSet.getString("status");
                RentalRecord rentalRecord = new RentalRecord(rentalId, customerId, carId, startDate, expectedReturnDate, actualReturnDate, rentalFee, status);
                rentalRecords.add(rentalRecord);
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
        return rentalRecords;
    }

    public List<RentalRecord> getAllRentalRecords() {
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM rentalrecord");
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int rentalId = resultSet.getInt("rental_id");
                int customerId = resultSet.getInt("customer_id");
                int carId = resultSet.getInt("car_id");
                Date startDate = resultSet.getDate("start_date");
                Date expectedReturnDate = resultSet.getDate("expected_return_date");
                Date actualReturnDate = resultSet.getDate("actual_return_date");
                double rentalFee = resultSet.getDouble("rental_fee");
                String status = resultSet.getString("status");
                RentalRecord rentalRecord = new RentalRecord(rentalId, customerId, carId, startDate, expectedReturnDate, actualReturnDate, rentalFee, status);
                rentalRecords.add(rentalRecord);
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
        return rentalRecords;
    }

    /**
     * 添加租赁记录
     * 注意：添加租赁记录时，status默认是Ongoing
     */
    public RentalRecord addRentalRecord(int carId, int customerId, int duration) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;

        try {
            connection = DBConnectionPool.getConnection();
            String sql = "INSERT INTO rentalrecord (customer_id, car_id, start_date, expected_return_date, rental_fee, status) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, carId);
            // 设置起始日期, 今天, 精度到天
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            // 设置预计归还日期, 今天加上duration, 精度到天
            preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis() + duration * 24 * 60 * 60 * 1000L));
            // 设置租金
            preparedStatement.setDouble(5, new CarDao().getCarById(carId).getDailyRentalFee() * duration);
            // 设置状态
            preparedStatement.setString(6, "Ongoing");
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating rental record failed, no rows affected.");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                rentalRecord = new RentalRecord(
                        resultSet.getInt(1),
                        customerId,
                        carId,
                        new java.sql.Date(System.currentTimeMillis()),
                        new java.sql.Date(System.currentTimeMillis() + duration * 24 * 60 * 60 * 1000L),
                        null,
                        new CarDao().getCarById(carId).getDailyRentalFee() * duration,
                        "Ongoing"
                );
            } else {
                throw new SQLException("Creating rental record failed, no ID obtained.");
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
        return rentalRecord;
    }

    public RentalRecord endRentalRecord(int id, double extraFee) {
        RentalRecord rentalRecord = this.getRentalRecordsByID(id);
        Date returnDate = new Date(System.currentTimeMillis());
        rentalRecord.setActualReturnDate(returnDate);
        rentalRecord.setRentalFee(rentalRecord.getRentalFee() + extraFee);
        rentalRecord.setStatus("Completed");
        return this.updateRentalRecord(rentalRecord);
    }

    /**
     * 更新租赁记录
     * 注意：Status只能是Ongoing, Completed, Cancelled
     */
    public RentalRecord updateRentalRecord(RentalRecord rentalRecord){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord newRentalRecord = null;

        try {
            connection = DBConnectionPool.getConnection();
            String sql = "UPDATE rentalrecord SET " +
                    "customer_id =?, car_id =?, start_date =?, expected_return_date =?, " +
                    "actual_return_date =?, rental_fee =?, status =? WHERE rental_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rentalRecord.getCustomerId());
            preparedStatement.setInt(2, rentalRecord.getCarId());
            preparedStatement.setDate(3, rentalRecord.getStartDate());
            preparedStatement.setDate(4, rentalRecord.getExpectedReturnDate());
            if (rentalRecord.getActualReturnDate() != null) {
                preparedStatement.setDate(5, rentalRecord.getActualReturnDate());
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.setDouble(6, rentalRecord.getRentalFee());
            preparedStatement.setString(7, rentalRecord.getStatus());
            preparedStatement.setInt(8, rentalRecord.getRentalId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating rental record failed, no rows affected.");
            }
            newRentalRecord = this.getRentalRecordsByID(rentalRecord.getRentalId());
            if (newRentalRecord == null) {
                throw new SQLException("Updating rental record failed, no ID obtained.");
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

        return newRentalRecord;

    }
    public boolean processViolationAndPayment(int rentalRecordId, String violationType, String violationDescription, double fineAmount, String paymentMethod){
        return false;
    }

    private static StringBuilder getStringBuilder(Map<String, String> filterParams) {
        StringBuilder sb = new StringBuilder();
        if (filterParams != null) {
            for (Map.Entry<String, String> entry : filterParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null) {
                    sb.append(" AND ").append(key).append(" = '").append(value).append("'");
                }
            }
        }
        return sb;
    }
    // 测试代码
    public static void main(RentalRecord[] args){
        List<RentalRecord> rentalRecordList = new RentalRecordDao().getRentalRecordsByCustomerID(18, null);
        for (RentalRecord rentalRecord : rentalRecordList) {
            System.out.println(rentalRecord.getRentalId());
        }
    }
}

