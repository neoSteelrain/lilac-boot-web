package com.steelrain.springboot.lilac.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 회원의 프로필이미지의 형식을 검증하는 애노테이션
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProfileImageValidator.class)
@Documented
public @interface ProfileImageFormat {
    String message() default "지원하지 않는 이미지 형식입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
