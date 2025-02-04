package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Users.Client;
import org.fooddelivery.DomainModel.Users.ClientPointsHistory;
import org.fooddelivery.DomainModel.Users.ClientPromotionHistory;
import org.fooddelivery.DomainModel.Users.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.user.userId = :userId")
    Client findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Client c WHERE c.loyaltyPoints >= :minPoints")
    List<Client> findClientsWithMinLoyaltyPoints(@Param("minPoints") Integer minPoints);

    @Query("SELECT c.orders FROM Client c WHERE c.clientId = :clientId")
    List<Object> findOrderHistoryByClientId(@Param("clientId") Long clientId);

    @Query("SELECT p FROM ClientPointsHistory p WHERE p.client.clientId = :clientId ORDER BY p.pointsUpdatedAt DESC")
    List<ClientPointsHistory> findPointsHistoryByClientId(@Param("clientId") Long clientId);

    @Query("SELECT p FROM ClientPointsHistory p WHERE p.client.clientId = :clientId AND p.transactionType = :transactionType")
    List<ClientPointsHistory> findPointsByTransactionType(
            @Param("clientId") Long clientId,
            @Param("transactionType") TransactionType transactionType
    );

    @Query("SELECT p FROM ClientPromotionHistory p WHERE p.client.clientId = :clientId ORDER BY p.recordedAt DESC")
    List<ClientPromotionHistory> findPromotionHistoryByClientId(@Param("clientId") Long clientId);

    @Query("SELECT p FROM ClientPromotionHistory p WHERE p.client.clientId = :clientId AND p.recordedAt BETWEEN :startDate AND :endDate")
    List<ClientPromotionHistory> findPromotionsUsedBetween(
            @Param("clientId") Long clientId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT p FROM ClientPromotionHistory p WHERE p.client.clientId = :clientId AND p.discountAmount > :minDiscount")
    List<ClientPromotionHistory> findPromotionsWithMinDiscount(
            @Param("clientId") Long clientId,
            @Param("minDiscount") BigDecimal minDiscount
    );


}
