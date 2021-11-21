package com.nosorio.mailer.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosorio.mailer.models.MailBean;
import com.nosorio.mailer.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "${rabbitmq.queue}", durable = "false"),
        exchange = @Exchange(value = "${rabbitmq.exchange}", ignoreDeclarationExceptions = "true"),
        key = "mail.received"
))
public class Receiver {

    private final MailService mailService;
    private final CountDownLatch latch = new CountDownLatch(1);

    @RabbitHandler
    private void receiveEMail(String message) {
        try {
            log.info ("Mail received");
            MailBean mailBean = new ObjectMapper().readValue(message, MailBean.class);
            //enviar correo electrónico
            mailService.sendMail(mailBean);
            log.info ("Mail sent");
            latch.countDown();
        }
        catch(JsonProcessingException e) {
            log.error("RabbitMQ message couldn't be processed. Error: {}", e.getMessage());
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}