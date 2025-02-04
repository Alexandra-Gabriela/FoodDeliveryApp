package org.fooddelivery.DomainModel.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientPointsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pointsHistoryId;
    private Integer points;
    private TransactionType transactionType;
    private Date pointsUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
