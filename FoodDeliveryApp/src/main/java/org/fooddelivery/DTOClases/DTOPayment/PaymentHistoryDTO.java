package org.fooddelivery.DTOClases.DTOPayment;

import lombok.*;
import org.fooddelivery.DomainModel.Payment.PaymentStatus;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryDTO {
    private Integer paymentHistoryId;
    private PaymentStatus status;
    private Date processedAt;
    private String refundReason;

    private PaymentDTO payment;
}
