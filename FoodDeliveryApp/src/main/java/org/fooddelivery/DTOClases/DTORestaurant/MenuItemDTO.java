package org.fooddelivery.DTOClases.DTORestaurant;

import lombok.*;
import org.fooddelivery.DTOClases.DTOPromotion.PromotionRulesDTO;

import java.util.List;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MenuItemDTO {
    private Long itemId;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private String category;
    private Integer orderCount;
    private int stock;

    private RestaurantDTO restaurant;
    private List<PromotionRulesDTO> promotionRules;
}
