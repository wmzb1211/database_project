package com.EasyRide.dao;

import com.EasyRide.entity.ViolationRecord;

public interface ViolationRecordDao {
    public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount);
}
