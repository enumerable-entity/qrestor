package com.qrestor.mailer.sendgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.commons.kafka.dto.KafkaEmailSendRequestDTO;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomPersonalization extends Personalization {
    @JsonProperty(value = "dynamic_template_data")
    private Map<String, Object> dynamic_template_data;

    @Override
    public Map<String, Object> getDynamicTemplateData() {
        return Objects.requireNonNullElse(dynamic_template_data, Collections.emptyMap());
    }

    public void addTemplateData(String key, String value) {
        if (dynamic_template_data == null) {
            dynamic_template_data = new HashMap<>();
            dynamic_template_data.put(key, value);
        } else {
            dynamic_template_data.put(key, value);
        }
    }

    public void addTemplateData(KafkaEmailSendRequestDTO emailSendRequestDTO) {
        if (dynamic_template_data == null) {
            dynamic_template_data = new HashMap<>();
            dynamic_template_data.putAll(emailSendRequestDTO.params());
        } else {
            dynamic_template_data.putAll(emailSendRequestDTO.params());
        }
    }
}
