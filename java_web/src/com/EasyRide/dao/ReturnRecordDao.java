package com.EasyRide.dao;

import com.EasyRide.entity.ReturnRecord;
import com.EasyRide.entity.ViolationRecord;
import java.sql.Date;
import java.util.List;

public class ReturnRecordDao {
    public List<ReturnRecord> getReturnRecord(int rentalRecordId){
        return null;
    }
    public ReturnRecord addReturnRecord(int rentalRecordId, Date returnDate, String returnStatus, int adminId){
        return null;
    }
    public ReturnRecord updateReturnRecord(ReturnRecord returnRecord){
        return null;
    }
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount){
        return null;
    }
}
