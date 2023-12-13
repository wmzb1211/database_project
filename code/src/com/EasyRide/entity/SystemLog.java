package com.EasyRide.entity;

import java.sql.Timestamp;

public class SystemLog {
    private int logId;
    private String operationType;
    private String operationDescription;
    private int operatorId;
    private Timestamp operationDateTime;
    private String ipAddress;
    private String result;

    public SystemLog(int logId, String operationType, String operationDescription, int operatorId, Timestamp operationDateTime, String ipAddress, String result) {
        this.logId = logId;
        this.operationType = operationType;
        this.operationDescription = operationDescription;
        this.operatorId = operatorId;
        this.operationDateTime = operationDateTime;
        this.ipAddress = ipAddress;
        this.result = result;
    }
    // Getters and setters

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public Timestamp getOperationDateTime() {
        return operationDateTime;
    }

    public void setOperationDateTime(Timestamp operationDateTime) {
        this.operationDateTime = operationDateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
