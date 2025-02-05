package org.fooddelivery.DTOClases.DTOPromotion;

import lombok.*;
import org.fooddelivery.DTOClases.DTOOrder.OrderDTO;
import org.fooddelivery.DomainModel.Promotions.RuleType;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class PromotionDTO {
    private Long promotionId;
    private String description;
    private Date expiryDate;
    private Date createdAt;
    private RuleType promoType;
    private Double discountPercentage;
    private Boolean isActive;

    private List<OrderDTO> orders;
    private List<PromotionRulesDTO> promotionRules;
    private List<PromotionHistoryDTO> promotionHistories;
}
