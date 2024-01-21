package com.qrestor.mailer.sendgrid.dto;

import lombok.Data;

import java.util.List;

@Data
public class SendGripApiTemplateResponse {
    private List<DynamicTemplate> result;
}
