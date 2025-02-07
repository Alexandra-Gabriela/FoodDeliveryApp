package org.fooddelivery.DomainModel.Promotions;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Order.Orders;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer promoHistoryId;
    private BigDecimal appliedDiscount;
    private Date appliedAt;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}
