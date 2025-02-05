package org.fooddelivery.Services.Implements;

import org.fooddelivery.DomainModel.Promotions.Promotion;
import org.fooddelivery.Services.IPromotionService;
import org.fooddelivery.Services.Repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PromotionService implements IPromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion addPromotion(Promotion promotion) {
        promotion.setIsActive(true);
        return promotionRepository.save(promotion);
    }

    @Override
    public Optional<Promotion> getPromotionById(Long promotionId) {
        return promotionRepository.findById(promotionId);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public List<Promotion> getActivePromotions() {
        return promotionRepository.findActivePromotions();
    }

    @Override
    public List<Promotion> getExpiredPromotions() {
        return promotionRepository.findExpiredPromotions();
    }

    @Override
    public Promotion updatePromotion(Long promotionId, Promotion updatedPromotion) {
        return promotionRepository.findById(promotionId).map(existingPromotion -> {
            existingPromotion.setPromoType(updatedPromotion.getPromoType());
            existingPromotion.setDiscountPercentage(updatedPromotion.getDiscountPercentage());
            existingPromotion.setStartDate(updatedPromotion.getStartDate());
            existingPromotion.setExpiryDate(updatedPromotion.getExpiryDate());
            existingPromotion.setDescription(updatedPromotion.getDescription());
            existingPromotion.setIsActive(updatedPromotion.getIsActive());
            return promotionRepository.save(existingPromotion);
        }).orElseThrow(() -> new RuntimeException("Promoția nu a fost găsită!"));
    }

    @Override
    public void deletePromotion(Long promotionId) {
        if (promotionRepository.existsById(promotionId)) {
            promotionRepository.deleteById(promotionId);
        } else {
            throw new RuntimeException("Promoția nu există!");
        }
    }
}
