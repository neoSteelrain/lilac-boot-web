package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class LicenseScheduleResponseDTO {

    @JsonProperty("body")
    private Body body;
    @JsonProperty("header")
    private Header header;

    @Getter
    public static class Body {
        @JsonProperty("totalCount")
        private int totalcount;
        @JsonProperty("pageNo")
        private int pageno;
        @JsonProperty("numOfRows")
        private int numofrows;
        @JsonProperty("items")
        private List<LicenseSchedule> scheduleList;
    }

    @Getter
    public static class LicenseSchedule {
        @JsonProperty("pracPassDt")
        private String pracPassDt;
        @JsonProperty("pracExamEndDt")
        private String pracExamEndDt;
        @JsonProperty("pracExamStartDt")
        private String pracExamStartDt;
        @JsonProperty("pracRegEndDt")
        private String pracRegEndDt;
        @JsonProperty("pracRegStartDt")
        private String pracRegStartDt;
        @JsonProperty("docPassDt")
        private String docPassDt;
        @JsonProperty("docExamEndDt")
        private String docExamEndDt;
        @JsonProperty("docExamStartDt")
        private String docExamStartDt;
        @JsonProperty("docRegEndDt")
        private String docRegEndDt;
        @JsonProperty("docRegStartDt")
        private String docRegStartDt;
        @JsonProperty("description")
        private String description;
        @JsonProperty("qualgbNm")
        private String qualgbNm;
        @JsonProperty("qualgbCd")
        private String qualgbCd;
        @JsonProperty("implSeq")
        private int implSeq;
        @JsonProperty("implYy")
        private String implyy;
    }

    @Getter
    public static class Header {
        @JsonProperty("resultMsg")
        private String resultmsg;
        @JsonProperty("resultCode")
        private String resultcode;
    }
}
