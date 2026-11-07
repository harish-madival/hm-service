package com.hm.notification.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EmailRequest {
  @Email @NotBlank
  private String to;

  @NotBlank @Size(max = 150)
  private String subject;

  @NotBlank @Size(max = 10000)
  private String body;
}

