package com.EasyRide.dao;

import com.EasyRide.entity.ViolationRecord;
import com.EasyRide.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ViolationRecordDao {
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount){
        ViolationRecord violationRecord=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            connection= DBConnectionPool.getConnection();
            String sql="INSERT INTO violationrecord (rental_id, violation_type, description, fine_amount) VALUES (?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,returnRecordId);
            preparedStatement.setString(2,violationType);
            preparedStatement.setString(3,violationDescription);
            preparedStatement.setDouble(4,fineAmount);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows==0){
                throw new SQLException("Creating violation record failed, no rows affected.");
            }
            resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int violationId=resultSet.getInt(1);
                violationRecord=new ViolationRecord(violationId,returnRecordId,violationType,violationDescription,fineAmount);
            }else{
                throw new SQLException("Creating violation record failed, no ID obtained.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet!=null) resultSet.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(connection!=null) DBConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return violationRecord;
    }
    public ViolationRecord getViolationByRentalRecordId(int returnRecordId){
        ViolationRecord violationRecord=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=DBConnectionPool.getConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM violationrecord WHERE rental_id=?");
            preparedStatement.setInt(1,returnRecordId);
            preparedStatement.executeQuery();
            resultSet=preparedStatement.getResultSet();
            if(resultSet.next()){
                int violationId=resultSet.getInt("violation_id");
                String violationType=resultSet.getString("violation_type");
                String violationDescription=resultSet.getString("violation_description");
                double fineAmount=resultSet.getDouble("fine_amount");
                violationRecord=new ViolationRecord(violationId,returnRecordId,violationType,violationDescription,fineAmount);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return violationRecord;
    }

}
