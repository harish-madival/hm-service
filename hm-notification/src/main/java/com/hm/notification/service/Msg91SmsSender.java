package com.hm.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.hm.notification.model.SmsRequest;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Msg91SmsSender implements SmsSender {

  private final WebClient webClient;

  @Value("${sms.msg91.baseUrl}")
  private String baseUrl;

  @Value("${sms.msg91.authKey}")
  private String authKey;

  @Value("${sms.msg91.flowId}")
  private String flowId;

  @Value("${sms.msg91.senderId}")
  private String senderId;

  @Value("${sms.msg91.dltEntityId:}")
  private String dltEntityId;

  public Msg91SmsSender(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  @Override
  public void send(String requestId, SmsRequest req) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("flow_id", flowId);
    payload.put("sender", senderId);
    payload.put("short_url", false);
    payload.put("mobiles", req.getTo().replace("+", "")); // MSG91 expects without '+'
    // Map template variables that your Flow expects:
    payload.put("VAR1", req.getVar1() != null ? req.getVar1() : req.getMessage());
    payload.put("VAR2", req.getVar2());

    if (dltEntityId != null) {
      payload.put("dlt_entity_id", dltEntityId);
    }

    Mono<String> resp = webClient.post()
        .uri(baseUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .header("authkey", authKey)
        .bodyValue(payload)
        .retrieve()
        .bodyToMono(String.class);

    String body = resp.block(); // block inside async worker thread
    log.info("sms_sent requestId={} to={} provider=msg91 response={}", requestId, req.getTo(), body);
  }
}

