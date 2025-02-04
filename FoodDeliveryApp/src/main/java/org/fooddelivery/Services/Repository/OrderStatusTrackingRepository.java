package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Order.OrderStatusTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusTrackingRepository  extends JpaRepository<OrderStatusTracking, Long> {

}
