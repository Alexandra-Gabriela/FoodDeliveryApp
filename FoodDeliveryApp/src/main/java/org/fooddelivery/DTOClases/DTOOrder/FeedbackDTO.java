package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;
import org.fooddelivery.DomainModel.Order.FeedbackType;

import java.util.Date;

@Data @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FeedbackDTO {
    private Long feedbackId;
    private Double rating;
    private String comments;
    private Date createdAt;
    private FeedbackType feedbackType;
    private OrderDTO order;
    private ClientDTO client;
}
