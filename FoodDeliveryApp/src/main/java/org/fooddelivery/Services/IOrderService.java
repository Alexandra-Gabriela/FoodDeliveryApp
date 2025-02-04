package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Order.Order;
import org.fooddelivery.DomainModel.Order.OrderItem;
import org.fooddelivery.DomainModel.Order.Status;
import org.fooddelivery.DomainModel.Payment.Payment;

import java.util.List;

public interface IOrderService {

    Order createOrder(Long clientId, Long restaurantId, List<OrderItem> items);

    Order updateOrderStatus(Long orderId, Status newStatus);
    boolean cancelOrder(Long orderId, String reason);

    List<Order> getOrdersByClient(Long clientId);
    List<Order> getOrdersByRestaurant(Long restaurantId);
    List<Order> getActiveOrders();
    Payment processPayment(Long orderId, String paymentMethod);
}
