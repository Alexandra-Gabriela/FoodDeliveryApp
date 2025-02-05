package org.fooddelivery.DTOClases.DTOOrder;

import lombok.*;
import org.fooddelivery.DTOClases.DTORestaurant.MenuItemDTO;

@Setter @Data @NoArgsConstructor
@AllArgsConstructor @Getter
public class OrderItemDTO {
    private Long orderItemId;
    private Integer quantity;
    private Double itemPrice;
    private OrderDTO order;
    private MenuItemDTO menuItem;
}
