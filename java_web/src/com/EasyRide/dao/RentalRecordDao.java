package com.EasyRide.dao;

import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.ReturnRecord;
import java.sql.*;
import java.util.List;
import java.util.Map;

public interface RentalRecordDao {
    public List<RentalRecord> getRentalRecordsByID(int id);

    // status为null时，返回所有状态的rental record
    public List<RentalRecord> getRentalRecordsByCustomerID(int customerId, String status);
    public List<RentalRecord> getRentalRecordsByCarID(int carId);
    public RentalRecord getRentalRecordByFilter(Map<String, String> filterParams);
    public List<RentalRecord> getAllRentalRecords();
    public RentalRecord addRentalRecord(int carId, int customerId, int duration);
    public RentalRecord updateRentalRecord(RentalRecord rentalRecord);
    public boolean processViolationAndPayment(int rentalRecordId, String violationType, String violationDescription, double fineAmount, String paymentMethod);
}
