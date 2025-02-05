package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;
import org.fooddelivery.DomainModel.Order.OutcomeType;

import java.math.BigDecimal;
import java.util.Date;

@Data @Setter @Getter @NoArgsConstructor @AllArgsConstructor

public class OrderOutcomeDTO {
    private Integer outcomeId;
    private OutcomeType outcomeType;
    private BigDecimal deliveryRating;
    private String cancellationReason;
    private String undeliveredReason;
    private Date recordedAt;
    private OrderDTO order;

}
