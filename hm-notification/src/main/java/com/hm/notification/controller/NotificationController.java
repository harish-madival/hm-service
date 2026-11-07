package com.hm.notification.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hm.notification.model.EmailRequest;
import com.hm.notification.model.SmsRequest;
import com.hm.notification.service.EmailService;
import com.hm.notification.service.NotificationService;
import com.hm.notification.service.SmsService;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;
    
    @Autowired
    NotificationService notificationService;

    @PostMapping("/email")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body) {
        return emailService.sendEmail(to, subject, body);
    }

    @PostMapping("/sms")
    public String sendSms(@RequestParam String to,
                          @RequestParam String message) {
        return smsService.sendSms(to, message);
    }
    
    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@Validated @RequestBody EmailRequest request) {
      String reqId = UUID.randomUUID().toString();
      notificationService.queueEmail(reqId, request);
      return ResponseEntity.accepted().body("{\"requestId\":\"" + reqId + "\"}");
    }

    @PostMapping("/sms")
    public ResponseEntity<?> sendSms(@Validated @RequestBody SmsRequest request) {
      String reqId = UUID.randomUUID().toString();
      notificationService.queueSms(reqId, request);
      return ResponseEntity.accepted().body("{\"requestId\":\"" + reqId + "\"}");
    }
}

