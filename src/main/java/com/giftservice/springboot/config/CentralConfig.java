package com.giftservice.springboot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CentralConfig {

  private String entityServiceBaseUrl;

  @Value(value = "${user-service.base-url}")
  public void setEntityServiceBaseUrl(final String cUrl) {
    this.entityServiceBaseUrl = cUrl;
  }


}
