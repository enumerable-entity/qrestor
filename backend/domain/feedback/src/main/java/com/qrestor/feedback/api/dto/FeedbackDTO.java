package com.qrestor.feedback.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qrestor.feedback.commons.FeedbackType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class FeedbackDTO implements Serializable {

    @NotNull
    private int rating;
    private String comment;
    @NotNull
    private FeedbackType type;
    @NotNull
    private UUID restaurantId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID orderId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID itemId;
}
