package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;
import org.fooddelivery.DTOClases.DTOPromotion.PromotionDTO;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientPromotionHistoryDTO {
    private Long clientPromoHistoryId;
    private Integer pointsUsed;
    private BigDecimal discountAmount;
    private Date recordedAt;

    private PromotionDTO promotion;
}
