package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Order.Order;
import org.fooddelivery.DomainModel.Order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

    List<Order> findByClientId(Long clientId);
    List<Order> findByStatusNot(Status completedStatus);
    List<Order> findByRestaurantRestaurantId(Long restaurantId);

}
