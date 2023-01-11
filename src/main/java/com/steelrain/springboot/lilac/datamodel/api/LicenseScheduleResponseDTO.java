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
        private List<LicenseSchedule> items;
    }

    @Getter
    public static class LicenseSchedule {
        @JsonProperty("pracPassDt")
        private String pracpassdt;
        @JsonProperty("pracExamEndDt")
        private String pracexamenddt;
        @JsonProperty("pracExamStartDt")
        private String pracexamstartdt;
        @JsonProperty("pracRegEndDt")
        private String pracregenddt;
        @JsonProperty("pracRegStartDt")
        private String pracregstartdt;
        @JsonProperty("docPassDt")
        private String docpassdt;
        @JsonProperty("docExamEndDt")
        private String docexamenddt;
        @JsonProperty("docExamStartDt")
        private String docexamstartdt;
        @JsonProperty("docRegEndDt")
        private String docregenddt;
        @JsonProperty("docRegStartDt")
        private String docregstartdt;
        @JsonProperty("description")
        private String description;
        @JsonProperty("qualgbNm")
        private String qualgbnm;
        @JsonProperty("qualgbCd")
        private String qualgbcd;
        @JsonProperty("implSeq")
        private int implseq;
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
