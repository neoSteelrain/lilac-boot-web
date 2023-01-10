package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaruBookExistResposeDTO {

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response extends NaruErrorBaseResponseDTO {
        @JsonProperty("result")
        private Result result;
        @JsonProperty("request")
        private Request request;
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
