package com.steelrain.springboot.lilac.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProfileImageValidator.class)
@Documented
public @interface ProfileImageFormat {
    String message() default "지원하지 않는 이미지 형식입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
