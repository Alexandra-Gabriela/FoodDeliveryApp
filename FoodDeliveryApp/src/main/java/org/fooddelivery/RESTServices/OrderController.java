package org.fooddelivery.RESTServices;

import org.fooddelivery.DomainModel.Order.OrderCancellationHistory;
import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Order.OrderItem;
import org.fooddelivery.DomainModel.Order.Status;
import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.Services.IOrderService;
import org.fooddelivery.Services.Repository.OrderCancellationHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    private final OrderCancellationHistoryRepository cancellationHistoryRepository;
    public OrderController(IOrderService orderService, OrderCancellationHistoryRepository cancellationHistoryRepository) {
        this.orderService = orderService;
        this.cancellationHistoryRepository = cancellationHistoryRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestParam Long clientId, @RequestParam Long restaurantId, @RequestBody List<OrderItem> items) {
        Orders order = orderService.createOrder(clientId, restaurantId, items);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrdersByClient(orderId).stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Orders>> getOrdersByClient(@PathVariable Long clientId) {
        List<Orders> orders = orderService.getOrdersByClient(clientId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Orders>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        List<Orders> orders = orderService.getOrdersByRestaurant(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Orders>> getActiveOrders() {
        List<Orders> activeOrders = orderService.getActiveOrders();
        return ResponseEntity.ok(activeOrders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId, @RequestParam Status newStatus) {
        Orders updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }


    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Payment> processPayment(@PathVariable Long orderId, @RequestParam String paymentMethod) {
        Payment payment = orderService.processPayment(orderId, paymentMethod);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long orderId, @RequestParam String reason) {
        boolean isCancelled = orderService.cancelOrder(orderId, reason);
        return ResponseEntity.ok(isCancelled);
    }

    @GetMapping("/client/{clientId}/cancellations")
    public ResponseEntity<List<OrderCancellationHistory>> getClientCancellationHistory(@PathVariable Long clientId) {
        List<OrderCancellationHistory> cancellations = cancellationHistoryRepository.findAll();
        return ResponseEntity.ok(cancellations);
    }
}
