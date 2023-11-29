package com.giftservice.springboot.config.datasource;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.giftservice.springboot.config.secrets.ISecretManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DBConfig {

  @Autowired
  private ISecretManager ISecretManager;

  private String jdbcUrl;
  private String driverClassName;
  private Integer minimumIdle;
  private Integer maximumPoolSize;
  private String poolName;
  private Long idleTimeout;
  private Long maxLifetime;
  private String connectionTestQuery;
  private Long connectionTimeout;

  @Value("${gift-service.dbName}")
  private String dbName;

  @Bean
  @Primary
  public DataSource getDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setDriverClassName(driverClassName);
    config.setUsername(ISecretManager.getDbCredentials(dbName).getUser());
    config.setPassword(ISecretManager.getDbCredentials(dbName).getPassword());

    config.setPoolName(poolName);
    config.setMinimumIdle(minimumIdle);
    config.setMaximumPoolSize(maximumPoolSize);
    config.setIdleTimeout(idleTimeout);
    config.setMaxLifetime(maxLifetime);
    config.setConnectionTimeout(connectionTimeout);
    config.setConnectionTestQuery(connectionTestQuery);

    return new HikariDataSource(config);
  }

}
