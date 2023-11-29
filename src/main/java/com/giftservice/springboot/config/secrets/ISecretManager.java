package com.giftservice.springboot.config.secrets;

import com.giftservice.springboot.config.datasource.DBCredentials;
import com.giftservice.springboot.config.datasource.Secrets;

public interface ISecretManager {

  DBCredentials getDbCredentials(String dbName);

  Secrets getSecrets();
}
