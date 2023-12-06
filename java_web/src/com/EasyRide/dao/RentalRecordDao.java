package com.EasyRide.dao;

import com.EasyRide.entity.RentalRecord;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentalRecordDao {
    public List<RentalRecord> getRentalRecordsByRentalId(int id) {
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;
        try {
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM rentalrecord WHERE rental_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                int carId = resultSet.getInt("car_id");
                Date startDate = resultSet.getDate("start_date");
                Date expectedReturnDate = resultSet.getDate("expected_return_date");
                Date actualReturnDate = resultSet.getDate("actual_return_date");
                double rentalFee = resultSet.getDouble("rental_fee");
                String status = resultSet.getString("status");
                RentalRecord rentalRecord = new RentalRecord(id, customerId, carId, startDate, expectedReturnDate, actualReturnDate, rentalFee, status);
                rentalRecords.add(rentalRecord);
                return rentalRecords;
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

    // status为null时，返回所有状态的rental record
    public List<RentalRecord> getRentalRecordsByCustomerID(int customerId, String status){
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM rentalrecord WHERE customer_id = ?";
            if(status != null){
                sql += " AND status = ?";
            }
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            if(status != null){
                preparedStatement.setString(2, status);
            }
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalRecords;
    }
    public List<RentalRecord> getRentalRecordsByCarID(int carId){
        List<RentalRecord> rentalRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRecord rentalRecord = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM rentalrecord WHERE car_id = ?");
            preparedStatement.setInt(1, carId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rentalRecords;
    }
    public RentalRecord getRentalRecordByFilter(Map<String, String> filterParams){
        if(filterParams ==null|| filterParams.isEmpty()
    }
    public List<RentalRecord> getAllRentalRecords(){
        return null;
    }
    public RentalRecord addRentalRecord(int carId, int customerId, int duration){
        return null;
    }
    public RentalRecord updateRentalRecord(RentalRecord rentalRecord){
        return null;
    }
    public boolean processViolationAndPayment(int rentalRecordId, String violationType, String violationDescription, double fineAmount, String paymentMethod){
        return false;
    }
}
