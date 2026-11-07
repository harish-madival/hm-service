package com.hm.notification.service;

import com.hm.notification.model.EmailRequest;

public interface EmailSender {
  void send(String requestId, EmailRequest req);
}

