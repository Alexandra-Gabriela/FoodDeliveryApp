package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Orders, Long> {

    List<Orders> findByClient_ClientId(Long clientId);
    List<Orders> findByStatusNot(Status completedStatus);
    List<Orders> findByRestaurantRestaurantId(Long restaurantId);

}
