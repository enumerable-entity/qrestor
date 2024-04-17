package com.qrestor.mailer.sendgrid;

import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO.USER_NAME_PARAM;


@Slf4j
@Component
@RequiredArgsConstructor

public class EmailMessageSender {

    private final SendGridConfig sendGridConfig;

    @Value("${app.sendgrid.enabled}")
    private Boolean isSendGridEnabled;

    @SneakyThrows
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

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(preparedMail.build());
        Response response = sendGridClient.api(request);
        log.info("EMAIL SENT TO {} WITH STATUS CODE {}", emailSendRequestDTO.params().get(USER_NAME_PARAM), response.getStatusCode());

    }

}
