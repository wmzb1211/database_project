package com.EasyRide.dao;

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

public class ReturnRecordDao {
    public List<ReturnRecord> getReturnRecordByRentalRecordId(int rentalRecordId){
        List<ReturnRecord> returnRecords=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        ReturnRecord returnRecord=null;
        try{
            connection= DBConnectionPool.getConnection();
            String sql="SELECT * FROM returnrecord WHERE rental_id=?";
            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setInt(1,rentalRecordId);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                int returnId = resultSet.getInt("return_id");
                int rentalId = resultSet.getInt("rental_id");
                Date returnDateTime = resultSet.getDate("return_date_time");
                String vehicleConditionDescription = resultSet.getString("vehicle_condition_description");
                int adminId = resultSet.getInt("admin_id");
                returnRecord = new ReturnRecord(returnId, rentalId, returnDateTime, vehicleConditionDescription, adminId);
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
    public ReturnRecord addReturnRecord(int rentalRecordId, String vehicleConditionDescription, int adminId){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ReturnRecord returnRecord=null;
        try {
            connection =DBConnectionPool.getConnection();
            String sql="insert into returnrecord (rental_id, return_date_time, vehicle_condition_description, admin_id) values (?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            Date returnDate=new Date(System.currentTimeMillis());
            preparedStatement.setInt(1,rentalRecordId);
            preparedStatement.setDate(2,returnDate);
            preparedStatement.setString(3,vehicleConditionDescription);
            preparedStatement.setInt(4,adminId);

            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows==0){
                throw new SQLException("Creating return record failed, no rows affected.");
            }
            resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                returnRecord=new ReturnRecord(resultSet.getInt(1), rentalRecordId, returnDate, vehicleConditionDescription, adminId);
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
        return null;
    }
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount){
        return null;
    }

    // 测试代码
    public static void main(String[] args) {
        ReturnRecordDao returnRecordDao = new ReturnRecordDao();
        List<ReturnRecord> returnRecords = returnRecordDao.getReturnRecordByRentalRecordId(2553);

        for (ReturnRecord returnRecord : returnRecords) {
            System.out.println(returnRecord.getReturnId());
        }
    }
}
