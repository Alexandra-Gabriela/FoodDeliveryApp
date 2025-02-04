package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Restaurant.PopularRestaurants;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByCategory(String category);

    @Query("SELECT r FROM Restaurant r WHERE r.rating >= :minRating")
    List<Restaurant> findByMinRating(@Param("minRating") Double minRating);

    @Query("SELECT r FROM Restaurant r WHERE r.orderCount >= :minOrders")
    List<Restaurant> findByMinOrderCount(@Param("minOrders") Integer minOrders);

    @Query("SELECT pr FROM PopularRestaurants pr WHERE pr.date BETWEEN :startDate AND :endDate")
    List<PopularRestaurants> findPopularRestaurantsBetweenDates(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT pr FROM PopularRestaurants pr WHERE pr.isCurrentTop = true")
    List<PopularRestaurants> findCurrentTopRestaurants();

    @Query("SELECT pr FROM PopularRestaurants pr WHERE pr.restaurant.restaurantId = :restaurantId")
    List<PopularRestaurants> findPopularityHistoryByRestaurantId(@Param("restaurantId") Long restaurantId);




}
