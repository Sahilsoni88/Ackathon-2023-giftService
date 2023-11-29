package com.giftservice.springboot.core.values;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.text.MessageFormat;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class User {
  private String id;
  private String phone;
  private String email;
  private String name;
  private String ekey;
  private String gender;
  private String dob;
  private String pincode;

  @Override
  public String toString() {
    return MessageFormat.format("[id:{0}, name:{1}, phone:{2}, email:{3}]",
      this.getId(), this.getName(), this.getPhone(), this.getEmail());
  }

}
