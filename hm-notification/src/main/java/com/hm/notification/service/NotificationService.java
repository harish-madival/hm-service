package com.hm.notification.service;

import com.hm.notification.model.EmailRequest;
import com.hm.notification.model.SmsRequest;

public interface NotificationService {
	
  void queueEmail(String requestId, EmailRequest req);
  void queueSms(String requestId, SmsRequest req);
}

