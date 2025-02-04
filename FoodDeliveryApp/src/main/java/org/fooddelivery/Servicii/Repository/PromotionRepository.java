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

    // ---------- METODE PENTRU PROMOTION ----------

    // Găsește promoții active
    @Query("SELECT p FROM Promotion p WHERE p.expiryDate > CURRENT_DATE")
    List<Promotion> findActivePromotions();

    // Găsește promoții expirate
    @Query("SELECT p FROM Promotion p WHERE p.expiryDate <= CURRENT_DATE")
    List<Promotion> findExpiredPromotions();

    // Găsește promoții după tip
    List<Promotion> findByPromoType(String promoType);

    // Găsește promoții care oferă un discount mai mare decât un prag minim
    @Query("SELECT p FROM Promotion p WHERE p.discountPercentage >= :minDiscount")
    List<Promotion> findByMinDiscount(@Param("minDiscount") Double minDiscount);

    // ---------- METODE PENTRU PROMOTION HISTORY ----------

    // Găsește istoricul promoțiilor pentru o comandă specifică
    @Query("SELECT ph FROM PromotionHistory ph WHERE ph.order.orderId = :orderId")
    List<PromotionHistory> findHistoryByOrderId(@Param("orderId") Long orderId);

    // Găsește toate reducerile aplicate de o promoție specifică
    @Query("SELECT ph FROM PromotionHistory ph WHERE ph.promotion.promotionId = :promotionId")
    List<PromotionHistory> findHistoryByPromotionId(@Param("promotionId") Long promotionId);

    // ---------- METODE PENTRU PROMOTION RULES ----------

    // Găsește regulile de promoție pentru un anumit restaurant
    @Query("SELECT pr FROM PromotionRules pr WHERE pr.restaurant.restaurantId = :restaurantId")
    List<PromotionRules> findRulesByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Găsește regulile aplicabile unui anumit produs (MenuItem)
    @Query("SELECT pr FROM PromotionRules pr WHERE pr.menuItem.itemId = :menuItemId")
    List<PromotionRules> findRulesByMenuItemId(@Param("menuItemId") Long menuItemId);

    // Găsește regulile pentru un anumit tip de promoție (ex: Restaurant, Client, Product)
    @Query("SELECT pr FROM PromotionRules pr WHERE pr.ruleType = :ruleType")
    List<PromotionRules> findRulesByRuleType(@Param("ruleType") RuleType ruleType);
}