package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Order.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f WHERE f.order.restaurant.restaurantId = :restaurantId")
    List<Feedback> findByRestaurantId(@Param("restaurantId") Long restaurantId);
    List<Feedback> findByClientClientId(Long clientId);
    @Query("SELECT f FROM Feedback f WHERE f.order.courier.courierId = :courierId")
    List<Feedback> findByCourierId(@Param("courierId") Long courierId);
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.order.restaurant.restaurantId = :restaurantId")
    Double findAverageRatingByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT f FROM Feedback f WHERE f.createdAt BETWEEN :startDate AND :endDate")
    List<Feedback> findFeedbackBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Feedback> findByRatingLessThan(Double threshold);
    @Query("SELECT f.client.clientId, COUNT(f) FROM Feedback f GROUP BY f.client.clientId ORDER BY COUNT(f) DESC")
    List<Object[]> findTopClientsByFeedbackCount();



}
