package com.giftservice.springboot.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CentralUser {
  private long id;
  private String phone;
  private String ekey;
  private String firstName;
  private String lastName;
  private String gender;
  private String dob;

  @Builder.Default
  private String email = null;
  private List<AddressDetail> addressDetails;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @Builder
  public static class AddressDetail{
    private String id;
    private String pincode;
    private String state;
    private String line1;
    private String line2;
    private String defaultAddress;
  }

  public final User intoUser() {
    return User
      .builder()
      .id(String.valueOf(this.id))
      .phone(this.getPhone())
      .email(this.getEmail())
      .name(this.getFullName())
      .build();
  }

  public final String getFullName() {
    return MessageFormat.format("{0} {1}", this.getFirstName(), this.getLastName());
  }
}
