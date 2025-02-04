package org.fooddelivery.Services.Implements;

import org.fooddelivery.DomainModel.Restaurant.MenuItem;
import org.fooddelivery.DomainModel.Restaurant.PopularMenuItems;
import org.fooddelivery.Services.Repository.MenuItemRepository;
import org.fooddelivery.Services.IMenuItemWorkflow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MenuItemWorkflowService implements IMenuItemWorkflow {

    private final MenuItemRepository menuItemRepository;

    public MenuItemWorkflowService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    @Transactional
    public MenuItem addMenuItem(MenuItem menuItem) {
        if (menuItem == null || menuItem.getPrice() < 0) {
            throw new IllegalArgumentException("Invalid menu item data");
        }
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public boolean removeMenuItem(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            return false;
        }
        menuItemRepository.deleteById(menuItemId);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMenuItem(Long menuItemId, String name, String description, Double price, String category) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (name != null && !name.trim().isEmpty()) {
            menuItem.setName(name);
        }
        if (description != null) {
            menuItem.setDescription(description);
        }
        if (price != null && price >= 0) {
            menuItem.setPrice(price);
        }
        if (category != null) {
            menuItem.setCategory(category);
        }

        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMenuItemAvailability(Long menuItemId, boolean available) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItem.setAvailable(available);
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMenuItemStock(Long menuItemId, int newStock) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        menuItem.setStock(newStock);
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMenuItemPrice(Long menuItemId, Double newPrice) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        menuItem.setPrice(newPrice);
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    public List<MenuItem> getTrendingMenuItems() {
        return menuItemRepository.findMostOrderedMenuItems();
    }

    @Override
    @Transactional
    public boolean promoteMenuItem(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItem.setOrderCount(menuItem.getOrderCount() + 50); // Simulare promovare
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    public List<PopularMenuItems> getPopularMenuItemsBetweenDates(Date startDate, Date endDate) {
        return menuItemRepository.findPopularMenuItemsBetweenDates(startDate, endDate);
    }

    @Transactional
    public boolean decreaseMenuItemStock(Long menuItemId, int quantity) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (menuItem.getStock() < quantity) {
            throw new IllegalStateException("Not enough stock available");
        }

        menuItem.setStock(menuItem.getStock() - quantity);
        menuItemRepository.save(menuItem);
        return true;
    }


}

