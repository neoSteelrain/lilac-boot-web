package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Naru 도서관 API 호출시 에러가 발생한 경우 HTTP 200 응답코드, error 필드에 에러 메시지를 처리하기 위한 클래스
 * 200 응답코드라고 하더라도 error 필드를 검사해서 API 오류를 확인해야 한다
 *
 * - 나루 도서관 API 에러응답 예시:
 * <p>
 *     {
 *     "response": {
 *         "error": "도서관 코드를 확인하시기 바랍니다."
 *     }
 * </p>
 */
@Getter
public class NaruErrorBaseResponseDTO {

    /**
     * error 필드를 반환하는 경우를 대비하기 위해 넣어주는 속성
     */
    @JsonProperty("error")
    private String error;
}
