package com.qrestor.mailer.sendgrid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.mailer.sendgrid.dto.DynamicTemplate;
import com.qrestor.mailer.sendgrid.dto.SendGripApiTemplateResponse;
import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SendGridConfig {

    @Getter
    private final SendGrid sendGridClient;
    private final ObjectMapper objectMapper;
    private final Email from;
    private Map<String, String> dynamicTemplateIds;


    public SendGridConfig(SendGrid sendGridClient,
                          ObjectMapper objectMapper,
                          @Value("${app.sendgrid.from-email}") String fromEmail,
                          @Value("${app.global-name}") String globalName) {
        this.sendGridClient = sendGridClient;
        this.objectMapper = objectMapper;
        this.from = new Email(fromEmail, globalName);
        initDynamicTemplateIds();
    }


    public Mail getMail(KafkaEmailSendRequestDTO emailSendRequestDTO,
                        Personalization personalization) {
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(dynamicTemplateIds.get(emailSendRequestDTO.type().name()));
        personalization.addTo(new Email(emailSendRequestDTO.receiverEmail()));
        mail.addPersonalization(personalization);
        return mail;
    }

    public void initDynamicTemplateIds() {
        try {
            Request request = new Request();
            request.setMethod(Method.GET);
            request.setEndpoint("/templates");
            request.addQueryParam("generations", "dynamic");
            request.addQueryParam("page_size", "10");
            Response response = sendGridClient.api(request);
            dynamicTemplateIds = objectMapper.readValue(response.getBody(), SendGripApiTemplateResponse.class)
                    .getResult()
                    .stream()
                    .collect(Collectors.toMap(DynamicTemplate::getName, DynamicTemplate::getId));
            log.info("Fetched dynamic template ids map: {}", dynamicTemplateIds);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
