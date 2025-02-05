package org.fooddelivery.RESTServices;

import org.fooddelivery.DomainModel.Users.TransactionType;
import org.fooddelivery.Services.IClientPointsWorkflow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/clients")
public class ClientController {
    private final IClientPointsWorkflow clientPointsService;

    public ClientController(IClientPointsWorkflow clientPointsService) {
        this.clientPointsService = clientPointsService;
    }

    @PostMapping("/{clientId}/add-points")
    public ResponseEntity<String> addPoints(@PathVariable Long clientId,
                                            @RequestParam Integer points,
                                            @RequestParam TransactionType transactionType) {
        clientPointsService.addPoints(clientId, points, transactionType);
        return ResponseEntity.ok("Punctele au fost adăugate cu succes.");
    }

    @PostMapping("/{clientId}/redeem-points")
    public ResponseEntity<String> redeemPoints(@PathVariable Long clientId, @RequestParam Integer points) {
        boolean success = clientPointsService.redeemPoints(clientId, points);
        if (success) {
            return ResponseEntity.ok("Punctele au fost utilizate cu succes.");
        } else {
            return ResponseEntity.badRequest().body("Puncte insuficiente.");
        }
    }

    @PostMapping("/{clientId}/expire-points")
    public ResponseEntity<String> expirePoints(@PathVariable Long clientId) {
        clientPointsService.expirePoints(clientId);
        return ResponseEntity.ok("Punctele expirate au fost actualizate.");
    }

    @PostMapping("/{clientId}/refund-points")
    public ResponseEntity<String> refundPoints(@PathVariable Long clientId, @RequestParam Integer points) {
        clientPointsService.refundPoints(clientId, points);
        return ResponseEntity.ok("Punctele au fost returnate cu succes.");
    }

    @GetMapping("/{clientId}/points")
    public ResponseEntity<Integer> getClientPoints(@PathVariable Long clientId) {
        Integer points = clientPointsService.getClientPoints(clientId);
        return ResponseEntity.ok(points);
    }


}