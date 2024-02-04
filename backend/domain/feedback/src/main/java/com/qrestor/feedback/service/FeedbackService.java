package com.qrestor.feedback.service;

import com.qrestor.feedback.api.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    void placeFeedbacks(List<FeedbackDTO> feedbackLeaveRequest);
}
