package com.hm.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.hm.notification.model.EmailRequest;

@Slf4j
@Component
public class SmtpEmailSender implements EmailSender {

  private final JavaMailSender mailSender;

  public SmtpEmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void send(String requestId, EmailRequest req) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(req.getTo());
    msg.setSubject(req.getSubject());
    msg.setText(req.getBody());
    mailSender.send(msg);
    log.info("email_sent requestId={} to={}", requestId, req.getTo());
  }
}

