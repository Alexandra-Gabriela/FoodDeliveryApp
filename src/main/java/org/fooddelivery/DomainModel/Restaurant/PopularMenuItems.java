package org.fooddelivery.DomainModel.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularMenuItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer popularMenuItemId;
    private Date date;
    private Integer orderCount;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
}
