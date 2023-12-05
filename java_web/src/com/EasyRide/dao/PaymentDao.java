package com.EasyRide.dao;

import com.EasyRide.entity.Payment;

public interface PaymentDao {
    public Payment getPaymentByRentalRecordId(int rentalRecordId);
    public Payment addPayment(int rentalRecordId, int customerId, double amount, String paymentMethod);
    public Payment updatePayment(Payment payment);

}
