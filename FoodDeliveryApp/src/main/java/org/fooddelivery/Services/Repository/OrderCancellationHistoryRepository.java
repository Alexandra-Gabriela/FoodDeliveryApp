package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Order.OrderCancellationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderCancellationHistoryRepository extends JpaRepository<OrderCancellationHistory, Integer> {
    @Query("SELECT o FROM OrderCancellationHistory o WHERE o.cancelledAt BETWEEN :startDate AND :endDate")
    List<OrderCancellationHistory> findCancellationsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
