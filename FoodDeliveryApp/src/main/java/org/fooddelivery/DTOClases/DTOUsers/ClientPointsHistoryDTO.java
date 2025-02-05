package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;
import org.fooddelivery.DomainModel.Users.TransactionType;

import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientPointsHistoryDTO {
    private Integer pointsHistoryId;
    private Integer points;
    private TransactionType transactionType;
    private Date pointsUpdatedAt;
}
