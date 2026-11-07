package com.hm.notification.service;

import com.hm.notification.model.SmsRequest;

public interface SmsSender {
  void send(String requestId, SmsRequest req);
}

