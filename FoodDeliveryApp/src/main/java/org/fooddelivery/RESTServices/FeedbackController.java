package org.fooddelivery.RESTServices;

import org.fooddelivery.DomainModel.Order.Feedback;
import org.fooddelivery.Services.Repository.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Feedback>> getFeedbackByRestaurant(@PathVariable Long restaurantId) {
        List<Feedback> feedbacks = feedbackRepository.findByRestaurantId(restaurantId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/courier/{courierId}")
    public ResponseEntity<List<Feedback>> getFeedbackByCourier(@PathVariable Long courierId) {
        List<Feedback> feedbacks = feedbackRepository.findByCourierId(courierId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Feedback>> getFeedbackByClient(@PathVariable Long clientId) {
        List<Feedback> feedbacks = feedbackRepository.findByClient_ClientId(clientId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/restaurant/{restaurantId}/rating")
    public ResponseEntity<Double> getAverageRatingByRestaurant(@PathVariable Long restaurantId) {
        Double averageRating = feedbackRepository.findAverageRatingByRestaurantId(restaurantId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<Feedback>> getFeedbackBetweenDates(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<Feedback> feedbacks = feedbackRepository.findFeedbackBetweenDates(startDate, endDate);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/low-rating")
    public ResponseEntity<List<Feedback>> getLowRatingFeedback(@RequestParam Double threshold) {
        List<Feedback> feedbacks = feedbackRepository.findByRatingLessThan(threshold);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback updatedFeedback) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedback.setRating(updatedFeedback.getRating());
                    feedback.setComments(updatedFeedback.getComments());
                    feedback.setCreatedAt(updatedFeedback.getCreatedAt());
                    Feedback savedFeedback = feedbackRepository.save(feedback);
                    return ResponseEntity.ok(savedFeedback);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

