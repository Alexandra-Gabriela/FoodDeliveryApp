package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;

import java.util.Date;

@Data @NoArgsConstructor
@AllArgsConstructor @Setter @Getter
public class OrderCancellationHistoryDTO {
    private Integer cancellationId;
    private String cancellationReason;
    private Date cancelledAt;
    private String cancelledBy;
    private OrderDTO order;
}
