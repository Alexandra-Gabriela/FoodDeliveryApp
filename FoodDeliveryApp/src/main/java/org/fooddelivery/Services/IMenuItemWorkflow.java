package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.PopularMenuItems;

import java.util.Date;
import java.util.List;

public interface IMenuItemWorkflow {
    MenuItem addMenuItem(MenuItem menuItem);
    boolean removeMenuItem(Long menuItemId);
    boolean updateMenuItem(Long menuItemId, String name, String description, Double price, String category);
    boolean updateMenuItemAvailability(Long menuItemId, boolean available);
    boolean updateMenuItemStock(Long menuItemId, int newStock);
    boolean updateMenuItemPrice(Long menuItemId, Double newPrice);
    List<MenuItem> getTrendingMenuItems();
    boolean promoteMenuItem(Long menuItemId);
    List<PopularMenuItems> getPopularMenuItemsBetweenDates(Date startDate, Date endDate);
}
