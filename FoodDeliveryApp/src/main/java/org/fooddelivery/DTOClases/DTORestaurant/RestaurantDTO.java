package org.fooddelivery.DTOClases.DTORestaurant;

import lombok.*;

import java.util.List;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private Long restaurantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Integer orderCount;

    private List<MenuItemDTO> menuItems;
}
