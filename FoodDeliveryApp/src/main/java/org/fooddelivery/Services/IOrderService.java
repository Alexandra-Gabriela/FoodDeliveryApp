package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Order.OrderItem;
import org.fooddelivery.DomainModel.Order.Status;
import org.fooddelivery.DomainModel.Payment.Payment;

import java.util.List;

public interface IOrderService {

    Orders createOrder(Long clientId, Long restaurantId, List<OrderItem> items);

    Orders updateOrderStatus(Long orderId, Status newStatus);
    boolean cancelOrder(Long orderId, String reason);

    List<Orders> getOrdersByClient(Long clientId);
    List<Orders> getOrdersByRestaurant(Long restaurantId);
    List<Orders> getActiveOrders();
    Payment processPayment(Long orderId, String paymentMethod);
}
