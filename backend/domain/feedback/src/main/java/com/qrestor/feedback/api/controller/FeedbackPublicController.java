package com.qrestor.feedback.api.controller;

import com.qrestor.feedback.api.dto.FeedbackDTO;
import com.qrestor.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.qrestor.feedback.api.controller.RestEndpoints.FEEDBACK_PUBLISH;

@RestController
@RequiredArgsConstructor
public class FeedbackPublicController {
    public final FeedbackService feedbackService;

    @PostMapping(FEEDBACK_PUBLISH)
    public ResponseEntity<Void> publishNewFeedback(@RequestBody @Valid List<@Valid FeedbackDTO> feedbackLeaveRequests,
                                                   @PathVariable("restaurantId") UUID restaurantId) {
        feedbackLeaveRequests.forEach(feedbackDTO -> feedbackDTO.setRestaurantId(restaurantId));
        feedbackService.placeFeedbacks(feedbackLeaveRequests);
        return ResponseEntity.ok().build();
    }

}
