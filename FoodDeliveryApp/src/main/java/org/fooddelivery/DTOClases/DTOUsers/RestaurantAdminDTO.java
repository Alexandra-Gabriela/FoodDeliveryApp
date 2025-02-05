package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;
import org.fooddelivery.DTOClases.DTORestaurant.RestaurantDTO;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class RestaurantAdminDTO {
    private Long adminId;
    private RestaurantDTO restaurant;

}
