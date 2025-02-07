package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Users.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM Users u WHERE u.userType = :userType")
    List<Users> findUsersByType(@Param("userType") UserType userType);
    @Query("SELECT u FROM Users u WHERE u.createdAt >= :date")
    List<Users> findUsersCreatedAfter(@Param("date") Date date);
    @Query("SELECT u.totalDiscountsReceived FROM Users u WHERE u.userId = :userId")
    Integer findTotalDiscountsByUserId(@Param("userId") Long userId);
    @Query("SELECT c FROM Client c WHERE c.user.userId = :userId")
    Optional<Client> findClientByUserId(@Param("userId") Long userId);
    @Query("SELECT c.orders FROM Client c WHERE c.clientId = :clientId")
    List<Orders> findOrdersByClientId(@Param("clientId") Long clientId);
    @Query("SELECT cr FROM Courier cr WHERE cr.user.userId = :userId")
    Optional<Courier> findCourierByUserId(@Param("userId") Long userId);
    @Query("SELECT o FROM Orders o WHERE o.courier.courierId = :courierId")
    List<Orders> findOrdersByCourierId(@Param("courierId") Long courierId);
    @Query("SELECT ra FROM RestaurantAdmin ra WHERE ra.restaurant.restaurantId = :restaurantId")
    List<RestaurantAdmin> findAdminsByRestaurantId(@Param("restaurantId") Long restaurantId);


}
