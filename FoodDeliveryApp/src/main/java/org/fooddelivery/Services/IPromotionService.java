package org.fooddelivery.Services;

import org.fooddelivery.DomainModel.Promotions.Promotion;

import java.util.List;
import java.util.Optional;

public interface IPromotionService {
    Promotion addPromotion(Promotion promotion);
    Optional<Promotion> getPromotionById(Long promotionId);
    List<Promotion> getAllPromotions();
    List<Promotion> getActivePromotions();
    List<Promotion> getExpiredPromotions();
    Promotion updatePromotion(Long promotionId, Promotion updatedPromotion);
    void deletePromotion(Long promotionId);

}
