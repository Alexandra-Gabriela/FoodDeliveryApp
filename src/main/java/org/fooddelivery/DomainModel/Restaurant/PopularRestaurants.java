package org.fooddelivery.DomainModel.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularRestaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer popularRestaurantId;
    private Date date;
    private Integer orderCount;
    private Boolean isCurrentTop;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
