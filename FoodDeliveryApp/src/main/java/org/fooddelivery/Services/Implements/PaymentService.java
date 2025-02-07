package org.fooddelivery.Services.Implements;

import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.DomainModel.Payment.PaymentHistory;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;
import org.fooddelivery.DomainModel.Users.Client;
import org.fooddelivery.Services.IPaymentService;
import org.fooddelivery.Services.Repository.ClientRepository;
import org.fooddelivery.Services.Repository.OrderRepository;
import org.fooddelivery.Services.Repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, ClientRepository clientRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Payment createPayment(Long clientId, Long orderId, Double amount) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setClient(client);
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.Pending);
        payment.setCreatedAt(new Date());

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public boolean processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.Pending) {
            return false;
        }

        payment.setStatus(PaymentStatus.Completed);
        paymentRepository.save(payment);

        PaymentHistory history = new PaymentHistory();
        history.setPayment(payment);
        history.setStatus(PaymentStatus.Completed);
        history.setProcessedAt(new Date());

        return true;
    }

    @Override
    @Transactional
    public boolean refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.Completed) {
            return false;
        }

        payment.setStatus(PaymentStatus.Refunded);
        paymentRepository.save(payment);

        PaymentHistory history = new PaymentHistory();
        history.setPayment(payment);
        history.setStatus(PaymentStatus.Refunded);
        history.setProcessedAt(new Date());

        return true;
    }

    @Override
    public PaymentStatus getPaymentStatus(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return payment.getStatus();
    }

    @Override
    public List<PaymentHistory> getPaymentHistory(Long paymentId) {
        return paymentRepository.findHistoryByPaymentId(paymentId);
    }

    @Override
    public Double getTotalPaymentsByClient(Long clientId) {
        return paymentRepository.findTotalPaymentsByClient(clientId);
    }
}
