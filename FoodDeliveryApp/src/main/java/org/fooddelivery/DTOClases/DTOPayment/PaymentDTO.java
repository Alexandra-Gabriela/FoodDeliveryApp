package org.fooddelivery.DTOClases.DTOPayment;

import lombok.*;
import org.fooddelivery.DTOClases.DTOOrder.OrderDTO;
import org.fooddelivery.DTOClases.DTOUsers.ClientDTO;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;

import java.util.Date;
import java.util.List;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private String paymentMethod;
    private Double amount;
    private Double tipAmount;
    private PaymentStatus status;
    private Date createdAt;

    private OrderDTO order;
    private ClientDTO client;
    private List<PaymentHistoryDTO> paymentHistories;
}
