package com.giftservice.springboot.config.datasource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Secrets {

  @JsonProperty(value = "db")
  private List<DBCredentials> dbCredentials;

  @JsonProperty(value = "newrelic_license_key")
  private String newrelicLicenseKey;

  public List<DBCredentials> getDbCredentials() {
    return dbCredentials;
  }

  public void setDbCredentials(List<DBCredentials> dbCredentials) {
    this.dbCredentials = dbCredentials;
  }
}
