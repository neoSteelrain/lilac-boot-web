package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class NaruLibSearchByRegionResponseDTO {

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
        private int pagesize;
        @JsonProperty("pageNo")
        private int pageno;
        @JsonProperty("request")
        private Request request;
    }

    @Getter
    public static class Libs {
        @JsonProperty("lib")
        private Library lib;
    }

    @Getter
    public static class Library {
        @JsonProperty("BookCount")
        private String bookcount;
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

    public static class Request {
        @JsonProperty("pageSize")
        private String pagesize;
        @JsonProperty("pageNo")
        private String pageno;
    }
}
