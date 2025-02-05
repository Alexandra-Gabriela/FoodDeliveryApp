package org.fooddelivery.Services.Implements;

import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.PopularRestaurants;
import org.fooddelivery.DomainModel.Restaurant.Restaurant;
import org.fooddelivery.Services.IRestaurantService;
import org.fooddelivery.Services.Repository.MenuItemRepository;
import org.fooddelivery.Services.Repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public boolean deleteRestaurant(Long restaurantId) {
        if (restaurantRepository.existsById(restaurantId.longValue())) {
            restaurantRepository.deleteById(restaurantId.longValue());
            return true;
        }
        return false;
    }

    @Override
    public Restaurant updateRestaurantDetails(Long restaurantId, String name, String category, Double rating) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId.longValue());
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setName(name);
            restaurant.setCategory(category);
            restaurant.setRating(rating);
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    @Override
    public MenuItem addMenuItemToRestaurant(Long restaurantId, MenuItem menuItem, Integer stock) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId.longValue());
        if (restaurant.isPresent()) {
            menuItem.setRestaurant(restaurant.get());
            menuItem.setStock(stock);
            return menuItemRepository.save(menuItem);
        }
        return null;
    }

    @Override
    public boolean removeMenuItem(Long menuItemId) {
        if (menuItemRepository.existsById(menuItemId)) {
            menuItemRepository.deleteById(menuItemId);
            return true;
        }
        return false;
    }

    @Override
    public MenuItem updateMenuItem(Long menuItemId, String name, Double price, boolean available, Integer stock) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isPresent()) {
            MenuItem menuItem = optionalMenuItem.get();
            menuItem.setName(name);
            menuItem.setPrice(price);
            menuItem.setAvailable(available);
            menuItem.setStock(stock);
            return menuItemRepository.save(menuItem);
        }
        return null;
    }

    @Override
    public boolean updateMenuItemStock(Long menuItemId, Integer newStock) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isPresent()) {
            MenuItem menuItem = optionalMenuItem.get();
            menuItem.setStock(newStock);
            menuItemRepository.save(menuItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean setMenuItemAvailability(Long menuItemId, boolean available) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isPresent()) {
            MenuItem menuItem = optionalMenuItem.get();
            menuItem.setAvailable(available);
            menuItemRepository.save(menuItem);
            return true;
        }
        return false;
    }


    @Override
    public List<Restaurant> getRestaurantsByCategory(String category) {
        return restaurantRepository.findByCategory(category);
    }

    @Override
    public List<Restaurant> getRestaurantsByMinRating(Double minRating) {
        return restaurantRepository.findByMinRating(minRating);
    }

    @Override
    public List<Restaurant> getRestaurantsByMinOrders(Integer minOrders) {
        return restaurantRepository.findByMinOrderCount(minOrders);
    }

    @Override
    public List<Restaurant> searchRestaurants(String category, Double minRating, Integer minOrders, Double maxPrice) {
        return restaurantRepository.findAll();
    }


    @Override
    public List<PopularRestaurants> getPopularRestaurantsBetweenDates(Date startDate, Date endDate) {
        return restaurantRepository.findPopularRestaurantsBetweenDates(startDate, endDate);
    }

    @Override
    public List<PopularRestaurants> getCurrentTopRestaurants() {
        return restaurantRepository.findCurrentTopRestaurants();
    }

    @Override
    public List<PopularRestaurants> getPopularRestaurantsByPeriod(String period) {
        return restaurantRepository.findCurrentTopRestaurants();
    }


    @Override
    public List<Restaurant> recommendRestaurantsForClient(Long clientId, int limit) {
        return restaurantRepository.findAll();
    }


    @Override
    public List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findAvailableMenuItemsByRestaurant(restaurantId);
    }

    @Override
    public List<MenuItem> getMostOrderedMenuItems() {
        return menuItemRepository.findMostOrderedMenuItems();
    }
}
