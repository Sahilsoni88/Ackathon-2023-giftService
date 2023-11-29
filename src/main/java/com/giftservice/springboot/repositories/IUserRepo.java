package com.giftservice.springboot.repositories;

import org.apache.http.auth.AuthenticationException;

import javax.naming.ServiceUnavailableException;
import com.giftservice.springboot.models.User;
import java.io.IOException;


public interface IUserRepo {
  User fetch(final String userId)
    throws InternalError, IOException, ServiceUnavailableException, AuthenticationException;
  public User create(final User user)
          throws InternalError, IOException, ServiceUnavailableException, AuthenticationException;
}
