package org.fooddelivery.DomainModel.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentHistoryId;
    private PaymentStatus status;
    private Date processedAt;
    private String refundReason;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

}
