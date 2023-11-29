package com.giftservice.springboot.annotations;// @author Atul Agrawal

import com.giftservice.springboot.common.CommonRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import static com.giftservice.springboot.common.Constants.USER_ID;

@Component
public class FromMDCValidator implements ConstraintValidator<FromMDC, CommonRequest> {
  @Override
  public boolean isValid(CommonRequest commonRequest, ConstraintValidatorContext constraintValidatorContext) {
    return true;
//    if (StringUtils.isEmpty(MDC.get(USER_ID))) {
//      constraintValidatorContext.disableDefaultConstraintViolation();
//      constraintValidatorContext.buildConstraintViolationWithTemplate(Integer.toString(101)).addConstraintViolation();
//      return false;
//    }
//    commonRequest.setUserId(MDC.get(USER_ID));
//    return true;
  }
}
