package com.nosorio.mailer;

/*import java.util.concurrent.TimeUnit;

import com.nosorio.mailer.messaging.Receiver;
import com.nosorio.mailer.models.MailBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("mail-exchange", "mail.received", new MailBean("", "", "", "", ""));
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}*/