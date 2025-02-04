package org.fooddelivery.Services.Implements;

import org.fooddelivery.DomainModel.Users.Client;
import org.fooddelivery.DomainModel.Users.ClientPointsHistory;
import org.fooddelivery.DomainModel.Users.TransactionType;
import org.fooddelivery.Services.IClientPointsWorkflow;
import org.fooddelivery.Services.Repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ClientPointWorkflowService implements IClientPointsWorkflow {

    private final ClientRepository clientRepository;

    public ClientPointWorkflowService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void addPoints(Long clientId, Integer points, TransactionType transactionType) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setLoyaltyPoints(client.getLoyaltyPoints() + points);

        ClientPointsHistory history = new ClientPointsHistory();
        history.setClient(client);
        history.setPoints(points);
        history.setTransactionType(transactionType);
        history.setPointsUpdatedAt(new Date());

        client.getPointsHistory().add(history);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public boolean redeemPoints(Long clientId, Integer pointsToRedeem) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (client.getLoyaltyPoints() < pointsToRedeem) {
            return false;
        }

        client.setLoyaltyPoints(client.getLoyaltyPoints() - pointsToRedeem);

        ClientPointsHistory history = new ClientPointsHistory();
        history.setClient(client);
        history.setPoints(-pointsToRedeem);
        history.setTransactionType(TransactionType.Redeemed);
        history.setPointsUpdatedAt(new Date());

        client.getPointsHistory().add(history);
        clientRepository.save(client);
        return true;
    }

    @Override
    @Transactional
    public void expirePoints(Long clientId) {
        List<ClientPointsHistory> earnedPoints = clientRepository.findPointsByTransactionType(clientId, TransactionType.Earned);
        int expiredPoints = earnedPoints.stream().mapToInt(ClientPointsHistory::getPoints).sum();

        if (expiredPoints > 0) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            client.setLoyaltyPoints(0);

            ClientPointsHistory history = new ClientPointsHistory();
            history.setClient(client);
            history.setPoints(-expiredPoints);
            history.setTransactionType(TransactionType.Expired);
            history.setPointsUpdatedAt(new Date());

            client.getPointsHistory().add(history);
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void refundPoints(Long clientId, Integer points) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setLoyaltyPoints(client.getLoyaltyPoints() + points);

        ClientPointsHistory history = new ClientPointsHistory();
        history.setClient(client);
        history.setPoints(points);
        history.setTransactionType(TransactionType.Refunded);
        history.setPointsUpdatedAt(new Date());

        client.getPointsHistory().add(history);
        clientRepository.save(client);
    }

    @Override
    public Integer getClientPoints(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return client.getLoyaltyPoints();
    }
}
