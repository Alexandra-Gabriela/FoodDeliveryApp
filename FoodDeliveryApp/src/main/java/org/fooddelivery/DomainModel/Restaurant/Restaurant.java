package org.fooddelivery.DomainModel.Restaurant;

import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Promotions.PromotionRules;
import org.fooddelivery.DomainModel.Users.RestaurantAdmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Integer orderCount;
    private Date createdAt;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantAdmin> admins = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PopularRestaurants> popularRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionRules> promotionRules = new ArrayList<>();


}
