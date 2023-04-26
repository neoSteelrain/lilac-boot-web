package com.steelrain.springboot.lilac.validate;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 회원의 프로필 이미지가 png, jpg, jpeg 형식인지 검증하는 검증기
 */
public class ProfileImageValidator implements ConstraintValidator<ProfileImageFormat, MultipartFile> {
    private final static String PNG = "image/png";
    private final static String JPG = "image/jpg";
    private final static String JPEG = "image/jpeg";

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        String contentType = value.getContentType();
        return checkSupportedFileFormat(contentType);
    }

    /**
     * 멀티파트파일의 contentType이 지정된 이미지타입 인지 검사한다
     * png, jpg, jpeg 이미지타입만 받는다
     * mime type 에 대한 참고문서 : https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types
     * @param contentType
     * @return
     */
    private boolean checkSupportedFileFormat(String contentType) {
        return PNG.equals(contentType)
                || JPG.equals(contentType)
                || JPEG.equals(contentType);
    }
}
