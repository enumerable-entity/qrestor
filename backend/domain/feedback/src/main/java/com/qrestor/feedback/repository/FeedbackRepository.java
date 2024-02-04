package com.qrestor.feedback.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.feedback.entity.FeedbackEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends PublicRepository<FeedbackEntity, Long> {
}