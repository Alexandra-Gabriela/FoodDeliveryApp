package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.DomainModel.Payment.PaymentHistory;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, Long> {

    List<Payment> findByClient_ClientId(Long clientId);

    List<Payment> findByOrder_OrderId(Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.status = :status")
    List<Payment> findByStatus(@Param("status") PaymentStatus status);


    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.client.clientId = :clientId")
    Double findTotalPaymentsByClient(@Param("clientId") Long clientId);

    @Query("SELECT p FROM Payment p WHERE p.order.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsBetweenDates(@Param("startDate") java.util.Date startDate,
                                           @Param("endDate") java.util.Date endDate);

    //for payment history

    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.payment.paymentId = :paymentId")
    List<PaymentHistory> findHistoryByPaymentId(@Param("paymentId") Long paymentId);


    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.status IN :statuses")
    List<PaymentHistory> findFailedOrRefundedPayments(@Param("statuses") List<PaymentStatus> statuses);


    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.processedAt BETWEEN :startDate AND :endDate")
    List<PaymentHistory> findHistoryBetweenDates(@Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);


    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.status = :status")
    List<PaymentHistory> findAllRefundedPayments(@Param("status") PaymentStatus status);

}
