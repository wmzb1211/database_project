package com.EasyRide.entity;

import java.math.BigDecimal;

public class BrandStats {
    private String brand;
    private long rentalCount;
    private long totalRentalDays;
    private BigDecimal totalIncome;

    public BrandStats(String brand, long rentalCount, long totalRentalDays, BigDecimal totalIncome) {
        this.brand = brand;
        this.rentalCount = rentalCount;
        this.totalRentalDays = totalRentalDays;
        this.totalIncome = totalIncome;
    }

    public BrandStats() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(long rentalCount) {
        this.rentalCount = rentalCount;
    }

    public long getTotalRentalDays() {
        return totalRentalDays;
    }

    public void setTotalRentalDays(long totalRentalDays) {
        this.totalRentalDays = totalRentalDays;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}
