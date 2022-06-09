package com.caramellow.location.locationweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtilImpl implements EmailUtil{

    @Autowired
    private JavaMailSender sender; // automatically comes from java

    @Override
    public void sendEmail(String toAddress, String subject, String body) {
        // MIME - Multipurpose Internet Mail Extension
        MimeMessage message = sender.createMimeMessage();

        // create the body inside the createMimeMessage
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        sender.send(message);
    }
}



















