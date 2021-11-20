package com.nosorio.mailer.messaging;

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
    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitHandler
    private void receiveEMail(MailBean mailBean){
        log.info ("Correo recibido");
        //enviar correo electrónico
        mailService.sendMail(mailBean);
        log.info ("Correo enviado");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}