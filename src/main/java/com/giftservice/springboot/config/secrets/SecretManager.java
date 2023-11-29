package com.giftservice.springboot.config.secrets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftservice.springboot.config.datasource.DBCredentials;
import com.giftservice.springboot.config.datasource.Secrets;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class SecretManager implements ISecretManager {

  private Map<String, DBCredentials> dbCredentials;
  private Secrets secrets;

  @Value(value = "${secrets.file}")
  private String filePath;

  @PostConstruct
  void loadSecrets() throws IOException {

    try (InputStream stream = new FileInputStream(filePath)) {
      secrets = new ObjectMapper().readValue(stream, Secrets.class);
      dbCredentials = secrets.getDbCredentials().stream().collect(Collectors.toMap(DBCredentials::getName, dbCredential -> dbCredential));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("secret_file does not exists: " + filePath);
    }
  }

  @Override
  public DBCredentials getDbCredentials(String dbname) {
    return dbCredentials.get(dbname);
  }

  @Override
  public Secrets getSecrets() {
    return secrets;
  }
}
