package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;
import org.fooddelivery.DTOClases.DTOPayment.PaymentDTO;
import org.fooddelivery.DTOClases.DTOPromotion.PromotionDTO;
import org.fooddelivery.DTOClases.DTORestaurant.RestaurantDTO;
import org.fooddelivery.DTOClases.DTOUsers.ClientDTO;
import org.fooddelivery.DTOClases.DTOUsers.CourierDTO;
import org.fooddelivery.DomainModel.Order.Status;

import java.util.Date;
import java.util.List;

@Data @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Status status;
    private Double totalPrice;
    private Double discountedTotalPrice;
    private Date scheduledTime;
    private Date originalScheduledTime;
    private Boolean pickupOption;
    private Date createdAt;

    private ClientDTO client;
    private RestaurantDTO restaurant;
    private CourierDTO courier;
    private PromotionDTO promotion;

    private List<OrderItemDTO> orderItems;
    private List<FeedbackDTO> feedbackList;
    private List<PaymentDTO> payments;
    private List<OrderStatusTrackingDTO> statusTrackingList;
    private List<OrderCancellationHistoryDTO> cancellationHistoryList;
}
