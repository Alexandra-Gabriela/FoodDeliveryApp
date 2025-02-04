package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderOrderId(Long orderId);
    List<OrderItem> findByMenuItemMenuItemId(Long menuItemId);

    @Query("SELECT oi.menuItem.itemId, COUNT(oi) AS timesOrdered " +
            "FROM OrderItem oi " +
            "GROUP BY oi.menuItem.itemId " +
            "ORDER BY timesOrdered DESC")
    List<Object[]> findTopOrderedItems();

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.client.clientId = :clientId")
    List<OrderItem> findItemsByClientId(@Param("clientId") Long clientId);
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.createdAt BETWEEN :startDate AND :endDate")
    List<OrderItem> findItemsOrderedBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
