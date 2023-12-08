package com.EasyRide.dao;

import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.ReturnRecord;
import com.EasyRide.entity.ViolationRecord;
import com.EasyRide.entity.SystemLog;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemLogDao {
    public List<SystemLog> getSystemLogbyOperatorId(int operatorId){
        List<SystemLog> systemLogs= new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        SystemLog systemLog=null;
        try{
            connection= DBConnectionPool.getConnection();
            String sql="SELECT * FROM SystemLog";
            preparedStatement =connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                int logId = resultSet.getInt("log_id");
                String operationType = resultSet.getString("operation_type");
                String operationDescription = resultSet.getString("operation_description");
//                int operatorId = resultSet.getInt("operator_id");
                Timestamp operationDateTime = resultSet.getTimestamp("operation_date_time");
                String ipAddress = resultSet.getString("ip_address");
                String result = resultSet.getString("result");
                systemLog = new SystemLog(logId, operationType, operationDescription, operatorId, operationDateTime, ipAddress, result);
                systemLogs.add(systemLog);
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
        return systemLogs;


    }
    public SystemLog addSystemLog(String operationType, String operationDescription, int operatorId, Timestamp operationDateTime, String ipAddress, String result){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        SystemLog systemLog=null;
        try{
            connection=DBConnectionPool.getConnection();
            String sql="INSERT INTO SystemLog(operation_type, operation_description, operator_id, operation_date_time, ip_address, result) VALUES(?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,operationType);
            preparedStatement.setString(2,operationDescription);
            preparedStatement.setInt(3,operatorId);
            preparedStatement.setTimestamp(4,operationDateTime);
            preparedStatement.setString(5,ipAddress);
            preparedStatement.setString(6,result);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows==0){
                throw new SQLException("Creating SystemLog failed, no rows affected.");
            }
            resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int logId=resultSet.getInt(1);
                systemLog=new SystemLog(logId,operationType,operationDescription,operatorId,operationDateTime,ipAddress,result);
            }else{
                throw new SQLException("Creating SystemLog failed, no ID obtained.");
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
        return systemLog;
    }

}