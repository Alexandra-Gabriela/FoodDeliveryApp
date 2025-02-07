package org.fooddelivery.DomainModel.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusTrackingId;
    private String previousStatus;
    private Status newStatus;
    private Date changedAt;
    private String changedBy;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
}
