package com.EasyRide.dao;

import com.EasyRide.entity.ReturnRecord;
import com.EasyRide.entity.ViolationRecord;
import java.sql.Date;
import java.util.List;

public interface ReturnRecordDao {
    public List<ReturnRecord> getReturnRecord(int rentalRecordId);
    public ReturnRecord addReturnRecord(int rentalRecordId, Date returnDate, String returnStatus, int adminId);
    public ReturnRecord updateReturnRecord(ReturnRecord returnRecord);
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount);
}
