package com.EasyRide.dao;

import com.EasyRide.entity.Payment;
import com.EasyRide.util.DBConnectionPool;
import javafx.beans.binding.ObjectExpression;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            preparedStatement = connection.prepareStatement("INSERT INTO payment (rental_id, customer_id, payment_type, payment_date, amount, payment_method) " +
                    "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, rentalRecordId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setString(3, paymentType);
            Date date = new Date(System.currentTimeMillis());
            preparedStatement.setDate(4, date);
            preparedStatement.setDouble(5, amount);
            preparedStatement.setString(6, paymentMethod);
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
            preparedStatement = connection.prepareStatement("UPDATE payment SET payment_type = ?, payment_date = ?, amount = ?, payment_method = ? WHERE payment_id = ?");
            preparedStatement.setString(1, payment.getPaymentType());
            preparedStatement.setDate(2, payment.getPaymentDate());
            preparedStatement.setDouble(3, payment.getAmount());
            preparedStatement.setString(4, payment.getPaymentMethod());
            preparedStatement.setInt(5, payment.getPaymentId());
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
