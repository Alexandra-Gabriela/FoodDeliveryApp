package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.DomainModel.Payment.PaymentHistory;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;

import java.util.List;

public interface IPaymentService {
    Payment createPayment(Long clientId, Long orderId, Double amount);
    boolean processPayment(Long paymentId);
    boolean refundPayment(Long paymentId);
    PaymentStatus getPaymentStatus(Long paymentId);
    List<PaymentHistory> getPaymentHistory(Long paymentId);
    Double getTotalPaymentsByClient(Long clientId);
}
