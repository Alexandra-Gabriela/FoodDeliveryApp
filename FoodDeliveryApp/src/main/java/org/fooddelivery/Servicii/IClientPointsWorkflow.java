package org.fooddelivery.Servicii;

import org.fooddelivery.DomainModel.Users.TransactionType;

public interface IClientPointsWorkflow {
    void addPoints(Long clientId, Integer points, TransactionType transactionType);
    boolean redeemPoints(Long clientId, Integer pointsToRedeem);
    void expirePoints(Long clientId);
    void refundPoints(Long clientId, Integer points);
    Integer getClientPoints(Long clientId);
}
