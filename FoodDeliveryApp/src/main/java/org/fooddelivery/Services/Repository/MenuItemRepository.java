package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.PopularMenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.restaurantId = :restaurantId AND m.available = true")
    List<MenuItem> findAvailableMenuItemsByRestaurant(@Param("restaurantId") Long restaurantId);

    List<MenuItem> findByCategory(String category);

    @Query("SELECT m FROM MenuItem m WHERE m.price <= :maxPrice")
    List<MenuItem> findByMaxPrice(@Param("maxPrice") Double maxPrice);

    @Query("SELECT m FROM MenuItem m ORDER BY m.orderCount DESC")
    List<MenuItem> findMostOrderedMenuItems();

    @Query("SELECT p FROM PopularMenuItems p WHERE p.date BETWEEN :startDate AND :endDate")
    List<PopularMenuItems> findPopularMenuItemsBetweenDates(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT p FROM PopularMenuItems p WHERE p.menuItem.itemId = :menuItemId")
    List<PopularMenuItems> findPopularityHistoryByMenuItemId(@Param("menuItemId") Long menuItemId);

}
