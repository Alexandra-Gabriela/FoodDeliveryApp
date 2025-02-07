package org.fooddelivery.DomainModel.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Users.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String paymentMethod;
    private Double amount;
    private Double tipAmount;
    private PaymentStatus status;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentHistory> paymentHistories = new ArrayList<>();
}
