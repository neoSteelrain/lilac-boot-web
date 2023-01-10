package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaruBookExistResposeDTO {

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response {
        @JsonProperty("result")
        private Result result;
        @JsonProperty("request")
        private Request request;

        /*
        HTTP 200 응답코드이면서 error 필드를 반환하는 경우를 대비하기 위해
        내가 직접 넣어준 속성
         */
        @JsonProperty("error")
        private String error;
    }

    @Getter
    public static class Result {
        @JsonProperty("loanAvailable")
        private String loanavailable;
        @JsonProperty("hasBook")
        private String hasbook;
    }

    @Getter
    public static class Request {
        @JsonProperty("libCode")
        private String libcode;
        @JsonProperty("isbn13")
        private String isbn13;
    }
}
