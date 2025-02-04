package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Order.Order;
import org.fooddelivery.DomainModel.Users.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.userType = :userType")
    List<User> findUsersByType(@Param("userType") UserType userType);
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
    List<User> findUsersCreatedAfter(@Param("date") Date date);
    @Query("SELECT u.totalDiscountsReceived FROM User u WHERE u.userId = :userId")
    Integer findTotalDiscountsByUserId(@Param("userId") Long userId);
    @Query("SELECT c FROM Client c WHERE c.user.userId = :userId")
    Optional<Client> findClientByUserId(@Param("userId") Long userId);
    @Query("SELECT c.orders FROM Client c WHERE c.clientId = :clientId")
    List<Order> findOrdersByClientId(@Param("clientId") Long clientId);
    @Query("SELECT cr FROM Courier cr WHERE cr.user.userId = :userId")
    Optional<Courier> findCourierByUserId(@Param("userId") Long userId);
    @Query("SELECT o FROM Order o WHERE o.courier.courierId = :courierId")
    List<Order> findOrdersByCourierId(@Param("courierId") Long courierId);
    @Query("SELECT ra FROM RestaurantAdmin ra WHERE ra.restaurant.restaurantId = :restaurantId")
    List<RestaurantAdmin> findAdminsByRestaurantId(@Param("restaurantId") Long restaurantId);


}
