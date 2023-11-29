package com.giftservice.springboot.config.datasource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DBCredentials {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "user")
  private String user;

  @JsonProperty(value = "password")
  private String password;
}
