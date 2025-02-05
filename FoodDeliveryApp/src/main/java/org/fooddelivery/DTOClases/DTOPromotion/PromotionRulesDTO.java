package org.fooddelivery.DTOClases.DTOPromotion;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PromotionRulesDTO {
    private Integer ruleId;
    private String ruleType;
    private BigDecimal minOrderValue;
    private BigDecimal discountPercentage;
    private Boolean applicable;
    private Integer minItems;
    private Integer clientLoyaltyPointsNeeded;

    private RestaurantDTO restaurant;
    private MenuItemDTO menuItem;
    private PromotionDTO promotion;

}
