package com.qrestor.mailer.sendgrid;

import com.qrestor.commons.kafka.dto.KafkaEmailSendRequestDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.qrestor.commons.kafka.dto.KafkaEmailSendRequestDTO.USER_NAME_PARAM;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender {

    private final SendGridConfig sendGridConfig;

    @Value("${app.sendgrid.enabled}")
    private Boolean isSendGridEnabled;
    public void sendEmail(KafkaEmailSendRequestDTO emailSendRequestDTO) {
        if (Boolean.FALSE.equals(isSendGridEnabled)) {
            log.info("SENDGRID IS DISABLED! MOCK EMAIL SENT TO {}", emailSendRequestDTO.params().get(USER_NAME_PARAM));
            return;
        }
        CustomPersonalization personalization = new CustomPersonalization();
        personalization.addTemplateData(emailSendRequestDTO);
        SendGrid sendGridClient = sendGridConfig.getSendGridClient();
        Mail preparedMail = sendGridConfig.getMail(emailSendRequestDTO, personalization);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(preparedMail.build());
            Response response = sendGridClient.api(request);
            log.info("EMAIL SENT TO {} WITH STATUS CODE {}", emailSendRequestDTO.params().get(USER_NAME_PARAM), response.getStatusCode());
        } catch (IOException ex) {
            log.error("ERROR WHILE SENDING EMAIL TO {}", emailSendRequestDTO.params().get(USER_NAME_PARAM), ex);
            throw new RuntimeException(ex);
        }
    }

}
