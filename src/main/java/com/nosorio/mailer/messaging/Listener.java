package com.nosorio.mailer.messaging;

import com.nosorio.mailer.models.EmailBean;
import com.nosorio.mailer.services.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {

    private final MailService mailService;

    @Bean
    public Consumer<KStream<String, EmailBean>> receiveEmail() {
        return input -> input.foreach((s, message) -> {
            mailService.sendEmail(message);
            log.info("Email sent");
        });
    }
}