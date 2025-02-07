package org.fooddelivery.DomainModel.Order;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Users.Client;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    private Double rating;
    private String comments;
    private Date createdAt;
    private FeedbackType feedbackType;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
