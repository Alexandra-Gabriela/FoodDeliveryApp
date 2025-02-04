package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Order.OrderOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOutcomeRepository  extends JpaRepository<OrderOutcome, Long> {

}
