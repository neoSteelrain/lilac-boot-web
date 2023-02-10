package com.steelrain.springboot.lilac.datamodel.view;

import lombok.*;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * 강의노트 상세정보 페이지에서 사용할 form DTO
 * 화면출력을 위해서만 사용한다
 */
@Getter
@Setter
public class LectureNoteDetailDTO {
    private Long noteId;
    private String noteTitle;
    private int totalNoteCount;
    private int inProgressNoteCount;
    private int completedNoteCount;
    private String noteDescription;
    private String noteLicenseName;
    
    // 자격증정보, DB에서 가져온 데이타이므로 code값이 아닌 id값을 저장한다
    private Integer licenseId; // Service bean 사이의 코드값을 주고받기 위해 선언한 필드
    private Integer subjectId; // Service bean 사이의 코드값을 주고받기 위해 선언한 필드
    private LicenseInfo licenseInfo;
    
    // 도서정보
    private List<KakaoBookInfo> kakaoBookInfo;

    // 영상정보
    private List<LectureVideoPlayListInfo> videoPlayList;


    @Getter
    @Builder
    public static class KakaoBookInfo{
        private String isbn13;
        private String title;
        private String contents;
        private String url;
        private Timestamp publishDate;
        private String authors;
        private String publisher;
        private String translators;
        private Integer price;
        private Integer salePrice;
        private String thumbnail;
        private String status;
        private Timestamp regDate;
    }

    @Getter
    @Builder
    public static class LicenseInfo{
        // 자격증 id
        private int licenseId;
        // 자격증 코드
        private int licenseCode;
        // 자격증 이름
        private String licenseName;
        // 자격증 설명
        private String licenseDesc;
        // 진행단계
        private String licStep;
        // 종료일자
        private String licEndDate;
        // 자격증시험일정
        List<LicenseScheduleInfo> licenseScheduleList;
    }

    @Getter
    @Builder
    public static class LicenseScheduleInfo{
        // 구분
        private String category;

        // 필기원서접수기간
        private String docRegPeriod;

        // 필기시험기간
        private String docExam;

        // 필기합격 발표일
        private String docPass;

        // 실기원수접수기간
        private String pracReg;

        // 실기시험기간
        private String pracExam;

        // 최종합격 발표일
        private String pracPass;
    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LectureVideoPlayListInfo {
        // DB 컬럼에 매치되는 필드
        private Long noteId;
        private Long playListId;
        private String originalPlayListId;
        private Long channelId;

        @Setter
        private String channelTitle;

        private String title;
        private Timestamp publishDate;
        private String thumbnailMedium;
        private Integer videoCount;
        private Timestamp regDate;

        // 화면에 보여질 가공된 정보
        @Setter
        private Double progressStatus; // 진행상황
        @Setter
        private long totalDuration; // 재생목록에 속한 영상들의 재생시간을 합친 전체재생시간
        @Setter
        private String totalDurationFormattedString; // 전체재생시간을 '--시간 --분 --초' 로 포맷팅한 문자열
    }
}
