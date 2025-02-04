package org.fooddelivery.Services.Implements;


import org.fooddelivery.DomainModel.Order.Order;
import org.fooddelivery.DomainModel.Order.OrderItem;
import org.fooddelivery.DomainModel.Order.Status;
import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;
import org.fooddelivery.Services.IOrderService;
import org.fooddelivery.Services.Repository.OrderRepository;
import org.fooddelivery.Services.Repository.OrderItemRepository;
import org.fooddelivery.Services.Repository.PaymentRepository;
import org.fooddelivery.Services.Repository.ClientRepository;
import org.fooddelivery.Services.Repository.RestaurantRepository;
import org.fooddelivery.DomainModel.Users.Client;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        PaymentRepository paymentRepository, ClientRepository clientRepository,
                        RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Long clientId, Long restaurantId, List<OrderItem> items) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);

        if (clientOpt.isPresent() && restaurantOpt.isPresent()) {
            Order order = new Order();
            order.setClient(clientOpt.get());
            order.setRestaurant(restaurantOpt.get());
            order.setStatus(Status.Pending);
            order.setOrderItems(items);

            double totalAmount = items.stream()
                    .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                    .sum();
            order.setTotalPrice(totalAmount);

            Order savedOrder = orderRepository.save(order);
            items.forEach(item -> {
                item.setOrder(savedOrder);
                orderItemRepository.save(item);
            });

            return savedOrder;
        }
        throw new IllegalArgumentException("Invalid client or restaurant ID");
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Status newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        throw new IllegalArgumentException("Order not found");
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long orderId, String reason) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == Status.Pending || order.getStatus() == Status.Completed) {
                order.setStatus(Status.Cancelled);
                orderRepository.save(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Order> getOrdersByClient(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    @Override
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantRestaurantId(restaurantId);
    }

    @Override
    public List<Order> getActiveOrders() {
        return orderRepository.findByStatusNot(Status.Completed);
    }

    @Override
    @Transactional
    public Payment processPayment(Long orderId, String paymentMethod) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            if (order.getStatus() != Status.Pending) {
                throw new IllegalStateException("Payment can only be processed for pending orders.");
            }

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalPrice());
            payment.setPaymentMethod(paymentMethod);
            payment.setStatus(PaymentStatus.Completed);

            order.setStatus(Status.Completed);
            orderRepository.save(order);
            return paymentRepository.save(payment);
        }
        throw new IllegalArgumentException("Order not found");
    }
}
