package com.EasyRide.dao;

import com.EasyRide.entity.Payment;
import com.EasyRide.util.DBConnectionPool;
import javafx.beans.binding.ObjectExpression;

import java.text.SimpleDateFormat;
//import java.util.Date;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class PaymentDao {
    /**
     *
     * @param rentalRecordId
     * @return
     */
    public List<Payment> getPaymentByRentalRecordId(int rentalRecordId) {
        List<Payment> payment = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM payment WHERE rental_id = ?");
            preparedStatement.setInt(1, rentalRecordId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int rentalId = resultSet.getInt("rental_id");
                int customerId = resultSet.getInt("customer_id");
                double amount = resultSet.getDouble("amount");
                String paymentMethod = resultSet.getString("payment_method");
                String paymentType = resultSet.getString("payment_type");
                Date paymentDate = resultSet.getDate("payment_date");
                payment.add(new Payment(paymentId, rentalId, paymentType, paymentDate, amount, paymentMethod));
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
        return payment;
    }
    // 查找从开始日期的支付信息，求和
    public double getPaymentByStartDate(Date StartDate) {
        double TotalPayment = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT SUM(amount) FROM payment WHERE payment_date >= ?");
//          StartDate = dateFormat.parse(StartDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            java.sql.Date date = new java.sql.Date(dateFormat.parse(StartDate).getTime());
            preparedStatement.setDate(1, StartDate);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                TotalPayment = resultSet.getDouble(1);
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

        return TotalPayment;

    }

    public List<Double> getPaymentInPastYear() {
        List<Double> paymentsInPastYear = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 获取当前日期
        Date currentDate = new Date(System.currentTimeMillis());
        calendar.setTime(currentDate);

        // 迭代过去的十二个月
        double sum = 0;
        for (int i = 0; i < 12; i++) {
            // 将日期调整到当前月的第一天
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            // 将日期调整到上一个月

            // 获取上一个月的第一天的日期
            Date startDate = new Date(calendar.getTimeInMillis());
//            Date startDate =
            // 获取上一个月的支付总和
            double payment = getPaymentByStartDate(startDate) - sum ;
            sum += payment;
            // 将支付总和添加到列表中
            paymentsInPastYear.add(payment);
            calendar.add(Calendar.MONTH, -1);

        }

        // 将列表反转，使得列表中的数据按照时间顺序排列
        Collections.reverse(paymentsInPastYear);

        return paymentsInPastYear;
    }




    /**
     *
     * 在数据库中添加一条payment后，使用returnGeneratedKeys()方法获取自增的paymentId，用affectedRows判断是否添加成功，成功则返回payment对象，否则弹出异常
     * @param rentalRecordId, customerId, amount, paymentMethod, paymentType
     * @return payment
     */
    public Payment addPayment(int rentalRecordId, int customerId, double amount, String paymentMethod, String paymentType){
        Payment payment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO payment (rental_id, customer_id,payment_date, amount, payment_method) " +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, rentalRecordId);
            preparedStatement.setInt(2, customerId);
//            preparedStatement.setString(3, paymentType);
            Date date = new Date(System.currentTimeMillis());
            preparedStatement.setDate(3, date);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setString(5, paymentMethod);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                payment = new Payment(resultSet.getInt(1), rentalRecordId, paymentType,
                        date, amount, paymentMethod);
            } else {
                throw new SQLException("Creating payment failed, no ID obtained.");
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
        return payment;
    }

    public Payment updatePayment(Payment payment){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment updatedPayment = null;
        try{
            connection = DBConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE payment SET payment_date = ?, amount = ?, payment_method = ? WHERE payment_id = ?");
//            preparedStatement.setString(1, payment.getPaymentType());
            preparedStatement.setDate(1, payment.getPaymentDate());
            preparedStatement.setDouble(2, payment.getAmount());
            preparedStatement.setString(3, payment.getPaymentMethod());
            preparedStatement.setInt(4, payment.getPaymentId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating payment failed, no rows affected.");
            }
            updatedPayment = payment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updatedPayment;
    }


    // 测试代码
    public static void main(String[] args) {
        PaymentDao paymentDao = new PaymentDao();
        List<Payment> payments = paymentDao.getPaymentByRentalRecordId(2504);
        for (Payment payment : payments){
            System.out.println(payment.getPaymentId() + " " + payment.getRentalId() + " " + payment.getPaymentType() + " " + payment.getPaymentDate() + " " + payment.getAmount() + " " + payment.getPaymentMethod());
        }
    }




}




