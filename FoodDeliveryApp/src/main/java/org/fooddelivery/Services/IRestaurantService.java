package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.PopularRestaurants;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;

import java.util.Date;
import java.util.List;

public interface IRestaurantService {

    // CRUD Restaurante
    Restaurant addRestaurant(Restaurant restaurant);
    boolean deleteRestaurant(Long restaurantId);
    Restaurant updateRestaurantDetails(Long restaurantId, String name, String category, Double rating);

    // Gestionare Meniu
    MenuItem addMenuItemToRestaurant(Long restaurantId, MenuItem menuItem, Integer stock);
    boolean removeMenuItem(Long menuItemId);
    MenuItem updateMenuItem(Long menuItemId, String name, Double price, boolean available, Integer stock);
    boolean updateMenuItemStock(Long menuItemId, Integer newStock);
    boolean setMenuItemAvailability(Long menuItemId, boolean available);

    // Căutare Restaurante
    List<Restaurant> getRestaurantsByCategory(String category);
    List<Restaurant> getRestaurantsByMinRating(Double minRating);
    List<Restaurant> getRestaurantsByMinOrders(Integer minOrders);
    List<Restaurant> searchRestaurants(String category, Double minRating, Integer minOrders, Double maxPrice);

    // Restaurante Populare
    List<PopularRestaurants> getPopularRestaurantsBetweenDates(Date startDate, Date endDate);
    List<PopularRestaurants> getCurrentTopRestaurants();
    List<PopularRestaurants> getPopularRestaurantsByPeriod(String period);

    // Recomandări Personalizate
    List<Restaurant> recommendRestaurantsForClient(Long clientId, int limit);

    // Gestionare Meniu
    List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId);
    List<MenuItem> getMostOrderedMenuItems();
}
