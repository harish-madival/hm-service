package com.hm.notification.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SmsRequest {
  // E.164 format e.g. +9198XXXXXXXX
  @NotBlank
  @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone must be E.164, e.g. +9198XXXXXXX")
  private String to;

  @NotBlank @Size(max = 1000)
  private String message;

  // Optional: template variables if using MSG91 flows
  private String var1;
  private String var2;

}

