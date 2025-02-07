package org.fooddelivery.DomainModel.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancellationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cancellationId;
    private String cancellationReason;
    private Date cancelledAt;
    private String cancelledBy;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
}
