package com.LTUC.Eventure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    public JavaMailSender javaMailSender;
public void sendEmail(String body, String subject, String toEmail){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("eventure.ltuc@gmail.com");
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);
    javaMailSender.send(message);
    System.out.println("sending successfully...");
}

}
