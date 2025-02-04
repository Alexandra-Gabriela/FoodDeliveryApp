package org.fooddelivery.DomainModel.Users;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Promotions.Promotion;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientPromotionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_promo_history_id;
    private Integer pointsUsed = 0;
    private BigDecimal discountAmount;
    private Date recordedAt;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;
}
