package com.nosorio.mailer.services;

import com.nosorio.mailer.models.MailBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // El buzón al que se enviará
    private final static String TO_MAIL_ADDR = "osorionicolas95@gmail.com";

    /**
     * Enviar método de prueba por correo
     */
    public void sendMail() {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(sender);
        mimeMessage.setTo(TO_MAIL_ADDR);
        mimeMessage.setSubject ("SpringBoot integra JavaMail para realizar el envío de correo");
        mimeMessage.setText ("SpringBoot integra JavaMail para realizar el envío de mensajes de texto");
        mailSender.send(mimeMessage);
    }

    /**
     * Enviar correo simple
     * @param mailBean
     */
    public void sendMail(MailBean mailBean) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(mailBean.getDestinationAddrs());
            helper.setSubject(mailBean.getSubject());
            helper.setText(mailBean.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    /**
     * Enviar correo: el cuerpo del correo es HTML
     * @param mailBean
     */
    public void sendMailHtml(MailBean mailBean) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(mailBean.getDestinationAddrs());
            helper.setSubject(mailBean.getSubject());
            helper.setText(mailBean.getText(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    /**
     * Enviar correo electrónico adjunto
     * @param mailBean
     */
    public void sendMailAttachment(MailBean mailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(mailBean.getDestinationAddrs());
            helper.setSubject(mailBean.getSubject());
            helper.setText(mailBean.getText(), true);
            // Agrega el nombre del adjunto y el adjunto
            //helper.addAttachment(mailBean.getAttachmentFilename(), mailBean.getFile());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plantilla de correo electrónico enviada
     * @param mailBean
     */
    public void sendMailTemplate(MailBean mailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(mailBean.getDestinationAddrs());
            helper.setSubject(mailBean.getSubject());
            helper.setText(mailBean.getText(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
