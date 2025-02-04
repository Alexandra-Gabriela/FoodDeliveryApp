package org.fooddelivery.DomainModel.Users;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;


}
