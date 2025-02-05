package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {
    private Long courierId;
    private String vehicleType;
    private UserDTO user;
}
