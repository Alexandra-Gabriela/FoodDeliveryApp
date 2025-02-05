package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;
import org.fooddelivery.DomainModel.Order.Status;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusTrackingDTO {
    private Integer statusTrackingId;
    private String previousStatus;
    private Status newStatus;
    private Date changedAt;
    private String changedBy;
    private OrderDTO order;
}
