package com.nosorio.mailer.services;

import com.nosorio.mailer.models.EmailBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Send test email
     */
    public void sendEmail() {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(sender);
        mimeMessage.setTo(sender);
        mimeMessage.setSubject ("SpringBoot integra JavaMail para realizar el envío de correo");
        mimeMessage.setText ("SpringBoot integra JavaMail para realizar el envío de mensajes de texto");
        mailSender.send(mimeMessage);
    }

    /**
     * Send mail: mail body is HTML
     * @param emailBean reference of [@MailBean]
     */
    public void sendEmail(EmailBean emailBean) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(emailBean.getDestinationAddrs());
            helper.setSubject(emailBean.getSubject());
            final Locale locale = Locale.ENGLISH;
            final Context thymeleafContext = new Context(locale);
            thymeleafContext.setVariable("recipientName", emailBean.getDestinationAddrs());
            thymeleafContext.setVariable("body", emailBean.getBody());

            String htmlBody = thymeleafTemplateEngine.process("email.html", thymeleafContext);
            helper.setText(htmlBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    /**
     * Send email attachment
     * @param emailBean reference of {@link EmailBean}
     */
    public void sendEmailAttachment(EmailBean emailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(emailBean.getDestinationAddrs());
            helper.setSubject(emailBean.getSubject());
            helper.setText(emailBean.getBody(), true);
            helper.addAttachment(emailBean.getAttachmentFilename(), emailBean.getFile());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
