package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
public class NaruLibSearchByBookResponseDTO {

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response extends NaruErrorBaseResponseDTO {
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
    }

    @Getter
    public static class Libs {
        @JsonProperty("lib")
        private Library lib;
    }

    @Getter
    public static class Library {
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

        /*
        @JsonProperty("loanAvailable")
        private String loanavailable;
        @JsonProperty("hasBook")
        private String hasbook;
         */
        @Getter
        @Setter
        private String loanAvailable;

        @Getter
        @Setter
        private String hasBook;
    }
}
