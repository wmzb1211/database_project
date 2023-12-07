package com.EasyRide.dao;

import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.ReturnRecord;
import com.EasyRide.entity.ViolationRecord;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Date;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnRecordDao {
    public List<ReturnRecord> getReturnRecordbyRentalRecordId(int rentalRecordId){
        List<ReturnRecord> returnRecords=null;
        Connection connection=null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        ReturnRecord returnRecord=null;
        try{
            connection= DBConnectionPool.getConnection();
            String sql="SELECT * FROM rentalrecord";
            preparedStatement =connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                int returnId = resultSet.getInt("return_id");
                int rentalId = resultSet.getInt("rental_id");
                int violationId = resultSet.getInt("violation_id");
                Date returnDateTime = resultSet.getDate("return_date_time");
                String vehicleConditionDescription = resultSet.getString("vehicle_condition_description");
                String fuelCondition = resultSet.getString("fuel_condition");
                String handlingPersonnel = resultSet.getString("handling_personnel");
                returnRecord = new ReturnRecord(returnId, rentalId, violationId, returnDateTime, vehicleConditionDescription, fuelCondition, handlingPersonnel);
                returnRecords.add(returnRecord);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet !=null) resultSet.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(connection!=null) DBConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return returnRecords;

    }
    public ReturnRecord addReturnRecord(int rentalRecordId, Date returnDate, int violationId, String vehicleConditionDescription, String fuelCondition, String handlingPersonnel){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ReturnRecord returnRecord=null;
        try {
            connection =DBConnectionPool.getConnection();
            String sql="INSERT INTO returnrecord (rental_id, violation_id,return_date_time,vehicle_condition_description,fuel_condition,handling_personnel) VALUES (?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,rentalRecordId);
            preparedStatement.setInt(2,violationId);
            preparedStatement.setDate(3,returnDate);
            preparedStatement.setString(4,vehicleConditionDescription);
            preparedStatement.setString(5,fuelCondition);
            preparedStatement.setString(6,handlingPersonnel);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows==0){
                throw new SQLException("Creating return record failed, no rows affected.");
            }
            resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                returnRecord=new ReturnRecord(resultSet.getInt(1),rentalRecordId,violationId,returnDate,vehicleConditionDescription,fuelCondition,handlingPersonnel);
            }else{
                throw new SQLException("Creating return record failed, no ID obtained.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet !=null) resultSet.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(connection!=null) DBConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return returnRecord;
    }
    public ReturnRecord updateReturnRecord(ReturnRecord returnRecord){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ReturnRecord updatedReturnRecord=null;
        try{
            connection =DBConnectionPool.getConnection();
            String sql="UPDATE returnrecord SET rental_id=?, violation_id=?,vehicle_condition_description=?,fuel_condition=?,handling_personnel=? WHERE return_id=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,returnRecord.getRentalId());
            preparedStatement.setInt(2,returnRecord.getViolationId());
            preparedStatement.setString(3,returnRecord.getVehicleConditionDescription());
            preparedStatement.setString(4,returnRecord.getFuelCondition());
            preparedStatement.setString(5,returnRecord.getHandlingPersonnel());
            preparedStatement.setInt(6,returnRecord.getReturnId());
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows==0){
                throw new SQLException("Updating return record failed, no rows affected.");
            }
            updatedReturnRecord=returnRecord;

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(resultSet !=null) resultSet.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(connection!=null) DBConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return updatedReturnRecord;
    }
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount){
        return null;
    }
}
