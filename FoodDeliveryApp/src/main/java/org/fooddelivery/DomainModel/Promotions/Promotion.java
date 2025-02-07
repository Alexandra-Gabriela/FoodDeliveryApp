package org.fooddelivery.DomainModel.Promotions;
import jakarta.persistence.*;
import lombok.*;
import org.fooddelivery.DomainModel.Order.Orders;
import org.fooddelivery.DomainModel.Users.ClientPromotionHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    private String description;
    private Date expiryDate;
    private Date createdAt;
    private RuleType promoType;
    private Double discountPercentage;
    @Column(nullable = false)
    private Boolean isActive = true;


    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionRules> promotionRules = new ArrayList<>();

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionHistory> promotionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientPromotionHistory> clientPromotionHistories = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
    public boolean isExpired() {
        return expiryDate != null && expiryDate.before(new Date());
    }


}
