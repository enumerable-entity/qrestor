package com.qrestor.auth.kafka.dto;

import com.qrestor.auth.common.enums.EmailRequestType;

import java.util.Map;

public record KafkaEmailSendRequestDTO (String receiverEmail, EmailRequestType type, Map<String, String > params){
}
