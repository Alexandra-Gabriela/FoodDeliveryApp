package org.fooddelivery.DomainModel.Payment;

public enum PaymentStatus {
    Pending,
    Completed,
    Failed,
    Refunded,
    Cancelled,
    Partially_paid;
}
