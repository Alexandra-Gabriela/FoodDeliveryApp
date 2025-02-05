package org.fooddelivery.DTOClases.DTOPromotion;

import lombok.*;
import org.fooddelivery.DTOClases.DTOOrder.OrderDTO;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class PromotionHistoryDTO {
    private Integer promoHistoryId;
    private BigDecimal appliedDiscount;
    private Date appliedAt;

    private OrderDTO order;
    private PromotionDTO promotion;
}
