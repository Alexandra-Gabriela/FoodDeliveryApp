package org.fooddelivery.DomainModel.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer outcomeId;

    private OutcomeType outcomeType;
    private BigDecimal deliveryRating;

    private String cancellationReason;
    private String undeliveredReason;
    private Date recordedAt ;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
}
