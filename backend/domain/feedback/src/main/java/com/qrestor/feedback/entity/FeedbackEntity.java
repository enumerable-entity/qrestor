package com.qrestor.feedback.entity;

import com.qrestor.feedback.commons.FeedbackType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "feedbacks", schema = "feedback")
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackType type;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "item_id", nullable = true)
    private UUID itemId;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;
}