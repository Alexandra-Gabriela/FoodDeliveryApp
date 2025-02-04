package org.fooddelivery.DomainModel.Promotions;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ruleId;
    private RuleType ruleType;
    private BigDecimal minOrderValue;
    private BigDecimal discountPercentage;
    private Boolean applicable = true;
    private Integer minItems;
    private Integer clientLoyaltyPointsNeeded;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}
