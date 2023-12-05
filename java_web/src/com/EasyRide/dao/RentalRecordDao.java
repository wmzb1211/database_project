package com.EasyRide.dao;

import com.EasyRide.entity.RentalRecord;

import java.util.List;
import java.util.Map;

public class RentalRecordDao {
    public List<RentalRecord> getRentalRecordsByID(int id){
        return null;
    }

    // status为null时，返回所有状态的rental record
    public List<RentalRecord> getRentalRecordsByCustomerID(int customerId, String status){
        return null;
    }
    public List<RentalRecord> getRentalRecordsByCarID(int carId){
        return null;
    }
    public RentalRecord getRentalRecordByFilter(Map<String, String> filterParams){
        return null;
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
