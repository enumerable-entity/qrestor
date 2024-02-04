package com.qrestor.feedback.service.impl;

import com.qrestor.feedback.api.dto.FeedbackDTO;
import com.qrestor.feedback.entity.FeedbackEntity;
import com.qrestor.feedback.mapper.FeedbackMapper;
import com.qrestor.feedback.repository.FeedbackRepository;
import com.qrestor.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public void placeFeedbacks(List<FeedbackDTO> feedbackLeaveRequest) {
        LocalDateTime now = LocalDateTime.now();
        List<FeedbackEntity> newFeedbacks = feedbackMapper.toEntities(feedbackLeaveRequest);
        newFeedbacks.forEach(feedback -> feedback.setPublishedAt(now));
        feedbackRepository.saveAllAndFlush(newFeedbacks);
    }
}
