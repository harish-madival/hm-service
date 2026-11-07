package com.hm.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hm.notification.model.EmailRequest;
import com.hm.notification.model.SmsRequest;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

  private final EmailSender emailSender;
  private final SmsSender smsSender;

  public NotificationServiceImpl(EmailSender emailSender, SmsSender smsSender) {
    this.emailSender = emailSender;
    this.smsSender = smsSender;
  }

  @Override
  public void queueEmail(String requestId, EmailRequest req) {
    sendEmailAsync(requestId, req);
  }

  @Override
  public void queueSms(String requestId, SmsRequest req) {
    sendSmsAsync(requestId, req);
  }

  @Async("notifyExecutor")
  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2.0))
  public void sendEmailAsync(String requestId, EmailRequest req) {
    emailSender.send(requestId, req);
  }

  @Async("notifyExecutor")
  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2.0))
  public void sendSmsAsync(String requestId, SmsRequest req) {
    smsSender.send(requestId, req);
  }
}

