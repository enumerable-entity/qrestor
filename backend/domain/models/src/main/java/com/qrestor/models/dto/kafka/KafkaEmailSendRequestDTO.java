package com.qrestor.models.dto.kafka;


import com.qrestor.models.EmailRequestType;

import java.util.Map;

public record KafkaEmailSendRequestDTO(String receiverEmail, EmailRequestType type, Map<String, String> params) {
    public static final String USER_NAME_PARAM = "username";
    public static final String URL_PARAM = "verification-link";
}
