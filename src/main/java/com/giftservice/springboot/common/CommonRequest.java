package com.giftservice.springboot.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import static com.giftservice.springboot.common.Constants.USER_ID;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommonRequest {
  private static final String EMPTY_USER_ID_ERR = "user id empty";

  private String userCookie;

//  public void validate() throws Exception {
//    if (StringUtils.isEmpty(trackerId)) {
//      throw new TrackerIdException(ErrorCode.INVALID_TRACKER_ID.getCode(), ErrorCode.INVALID_TRACKER_ID.getMessage());
//    }
//  }

  public void fromMDC() {
    this.userCookie = MDC.get(USER_ID);
  }
}
