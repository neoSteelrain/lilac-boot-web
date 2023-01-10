package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
public class NaruLibrarySearchByBookResponseDTO {

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response {
        @JsonProperty("libs")
        private List<Libs> libs;
        @JsonProperty("resultNum")
        private int resultnum;
        @JsonProperty("numFound")
        private int numfound;
        @JsonProperty("pageSize")
        private String pagesize;
        @JsonProperty("pageNo")
        private String pageno;
        
        /*
        HTTP 200 응답코드이면서 error 필드를 반환하는 경우를 대비하기 위해
        내가 직접 넣어준 속성
         */
        @JsonProperty("error")
        private String error;
    }

    @Getter
    public static class Libs {
        @JsonProperty("lib")
        private Lib lib;
    }

    @Getter
    public static class Lib {
        @JsonProperty("operatingTime")
        private String operatingtime;
        @JsonProperty("closed")
        private String closed;
        @JsonProperty("homepage")
        private String homepage;
        @JsonProperty("longitude")
        private String longitude;
        @JsonProperty("latitude")
        private String latitude;
        @JsonProperty("fax")
        private String fax;
        @JsonProperty("tel")
        private String tel;
        @JsonProperty("address")
        private String address;
        @JsonProperty("libName")
        private String libname;
        @JsonProperty("libCode")
        private String libcode;
    }
}
