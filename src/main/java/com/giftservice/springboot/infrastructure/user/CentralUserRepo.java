package com.giftservice.springboot.infrastructure.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftservice.springboot.config.CentralConfig;
import com.giftservice.springboot.exceptions.UserAuthenticationException;
import com.giftservice.springboot.models.CentralUser;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.repositories.IUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Repository;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.text.MessageFormat;

@Repository
@Log4j2
@RequiredArgsConstructor
public class CentralUserRepo implements IUserRepo {
  private static final String FETCH_USER_INFO_LOG = "CENTRAL_USER_SERVICE: fetching new user {0} with central service";
  private static final String FETCH_USER_SUCCESS_LOG = "CENTRAL_USER_SERVICE: new user {0} with central service fetched successfully";
  private static final String FETCH_USER_FAIL_LOG = "CENTRAL_USER_SERVICE: new user {0} with central service fetch failed";
  private static final OkHttpClient client = new OkHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private final CentralConfig centralConfig;

  @Override
  public User fetch(final String userId)
      throws InternalError, IOException, ServiceUnavailableException, AuthenticationException {

    log.info(MessageFormat.format(FETCH_USER_INFO_LOG, userId));

    final String url = MessageFormat.format("{0}/{1}?address_details=true",
        this.centralConfig.getEntityServiceBaseUrl(),
        EntityEndpoint.FETCH.getValue(),
        userId);
    final String cookieKeyValue = MessageFormat.format("user_id={0}", userId);
    final Request request = new Request.Builder().url(url).addHeader("Cookie", cookieKeyValue).build();
    final Response response = CentralUserRepo.client.newCall(request).execute();
    final String responseBody = response.body().string();
    log.info(url);
    if (response.code() != HttpStatus.SC_OK
        && response.code() != HttpStatus.SC_CREATED) {
      log.info(responseBody);
      log.info(MessageFormat.format(FETCH_USER_FAIL_LOG, userId));
      // NOTE: ERROR HANDLING
      switch (response.code()) {
        case HttpStatus.SC_BAD_REQUEST:
          throw new UserAuthenticationException("Authentication failed");
        case HttpStatus.SC_NOT_FOUND:
        case HttpStatus.SC_BAD_GATEWAY:
        case HttpStatus.SC_GATEWAY_TIMEOUT:
          throw new ServiceUnavailableException("");
        case HttpStatus.SC_FORBIDDEN:
        case HttpStatus.SC_UNAUTHORIZED:
          throw new AuthenticationException("");
        default:
          throw new InternalError("");
      }
    }

    final CentralUser centralUser = CentralUserRepo.objectMapper.readValue(responseBody, CentralUser.class);
    final User user = centralUser.intoUser();
    log.info(MessageFormat.format(FETCH_USER_SUCCESS_LOG, user));
    return user;
  }

  @Override
  public User create(final User user)
          throws InternalError, IOException, ServiceUnavailableException, AuthenticationException {

    final String url = MessageFormat.format("{0}/{1}?phone={2}",
            this.centralConfig.getEntityServiceBaseUrl(),
            EntityEndpoint.CREATE.getValue(),
            user.getPhone());

    final Request request = new Request.Builder().url(url).build();
    final Response response = CentralUserRepo.client.newCall(request).execute();
    final String responseBody = response.body().string();

    log.info(url);
    log.info(responseBody);

    if (response.code() != HttpStatus.SC_OK
            && response.code() != HttpStatus.SC_CREATED) {
      // NOTE: ERROR HANDLING
      switch (response.code()) {
        case HttpStatus.SC_BAD_REQUEST:
          throw new InternalError(response.body().string());
        case HttpStatus.SC_NOT_FOUND:
        case HttpStatus.SC_BAD_GATEWAY:
        case HttpStatus.SC_GATEWAY_TIMEOUT:
          throw new ServiceUnavailableException("");
        case HttpStatus.SC_FORBIDDEN:
        case HttpStatus.SC_UNAUTHORIZED:
          throw new AuthenticationException("");
        default:
          throw new InternalError("");
      }
    }

    final CentralUser centralUser = CentralUserRepo.objectMapper.readValue(responseBody, CentralUser.class);
    user.setId(String.valueOf(centralUser.getId()));
    return user;
  }

  public enum EntityEndpoint {
    FETCH("api/user/"),
    CREATE("api/user/");

    private final String value;

    EntityEndpoint(final String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }

}
