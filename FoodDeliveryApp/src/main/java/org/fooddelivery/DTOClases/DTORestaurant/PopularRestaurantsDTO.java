package org.fooddelivery.DTOClases.DTORestaurant;

import lombok.*;

import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PopularRestaurantsDTO {
    private Integer popularRestaurantId;
    private Date date;
    private Integer orderCount;
    private Boolean isCurrentTop;

    private RestaurantDTO restaurant;

}
