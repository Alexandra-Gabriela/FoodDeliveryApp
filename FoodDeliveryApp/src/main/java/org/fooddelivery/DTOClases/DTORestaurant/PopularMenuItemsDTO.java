package org.fooddelivery.DTOClases.DTORestaurant;

import lombok.*;

import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PopularMenuItemsDTO {
    private Integer popularMenuItemId;
    private Date date;
    private Integer orderCount;

    private MenuItemDTO menuItem;
}
