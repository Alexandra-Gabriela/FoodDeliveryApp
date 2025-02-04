package org.fooddelivery.Servicii.Repository;

import org.fooddelivery.DomainModel.Promotions.Promotion;
import org.fooddelivery.DomainModel.Promotions.PromotionHistory;
import org.fooddelivery.DomainModel.Promotions.PromotionRules;
import org.fooddelivery.DomainModel.Promotions.RuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    // ---------- PROMOTION ----------

    @Query("SELECT p FROM Promotion p WHERE p.expiryDate > CURRENT_DATE")
    List<Promotion> findActivePromotions();

    @Query("SELECT p FROM Promotion p WHERE p.expiryDate <= CURRENT_DATE")
    List<Promotion> findExpiredPromotions();

    List<Promotion> findByPromoType(String promoType);

    @Query("SELECT p FROM Promotion p WHERE p.discountPercentage >= :minDiscount")
    List<Promotion> findByMinDiscount(@Param("minDiscount") Double minDiscount);

    // ----------  PROMOTION HISTORY ----------

    @Query("SELECT ph FROM PromotionHistory ph WHERE ph.order.orderId = :orderId")
    List<PromotionHistory> findHistoryByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT ph FROM PromotionHistory ph WHERE ph.promotion.promotionId = :promotionId")
    List<PromotionHistory> findHistoryByPromotionId(@Param("promotionId") Long promotionId);

    // ----------PROMOTION RULES ----------

    @Query("SELECT pr FROM PromotionRules pr WHERE pr.restaurant.restaurantId = :restaurantId")
    List<PromotionRules> findRulesByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT pr FROM PromotionRules pr WHERE pr.menuItem.itemId = :menuItemId")
    List<PromotionRules> findRulesByMenuItemId(@Param("menuItemId") Long menuItemId);

    @Query("SELECT pr FROM PromotionRules pr WHERE pr.ruleType = :ruleType")
    List<PromotionRules> findRulesByRuleType(@Param("ruleType") RuleType ruleType);
}