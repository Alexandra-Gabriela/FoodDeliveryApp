package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientDTO {
    private Long clientId;
    private String address;
    private Integer loyaltyPoints;

    private UserDTO user;
    private List<ClientPointsHistoryDTO> pointsHistory;
    private List<ClientPromotionHistoryDTO> promotionHistory;
}
