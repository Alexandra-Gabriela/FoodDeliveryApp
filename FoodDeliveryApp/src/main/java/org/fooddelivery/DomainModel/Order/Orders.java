package org.fooddelivery.DomainModel.Order;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Payment.Payment;
import org.fooddelivery.DomainModel.Promotions.Promotion;
import org.fooddelivery.DomainModel.Promotions.PromotionHistory;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;
import org.fooddelivery.DomainModel.Users.Client;
import org.fooddelivery.DomainModel.Users.Courier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Status status;
    private Double totalPrice;
    private Double discountedTotalPrice;
    private Date scheduledTime;
    private Date originalScheduledTime;
    private Boolean pickupOption;
    private Date createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusTracking> statusTrackingList = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderCancellationHistory> cancellationHistoryList = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderOutcome outcome;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbackList = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionHistory> promotionHistoryList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;


}
