package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 도서관정보나루 API 중 도서 소장 도서관 조회 를 통해 가져온 도서관정보 DTO
 *
 */
@Getter
@Builder
public class NaruLibraryDTO {
    /*
    최근 변경된 DB 테이블스키마
    id	bigint	NO	PRI		auto_increment
    lib_code	int	NO	UNI		
    name	varchar(100)	YES			
    address	varchar(100)	YES			
    tel	varchar(20)	YES			
    fax	varchar(20)	YES			
    latitude	char(10)	YES			
    longitude	char(10)	YES			
    homepage	varchar(255)	YES			
    closed	varchar(255)	YES			
    operating_time	varchar(50)	YES			
    reg_date	datetime	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED
    update_date	datetime	YES			
     */

    private Long id;

    @Size(max=6)
    private String libCode;

    @Size(max=100)
    private String name;

    @Size(max=100)
    private String address;

    @Size(max=20)
    private String tel;

    @Size(max=20)
    private String fax;

    @Size(max=10)
    private String latitude;

    @Size(max=10)
    private String longitude;

    @URL
    @Size(max=255)
    private String homepage;

    @Size(max=255)
    private String closed;

    @Size(max=50)
    private String operatingTime;

    private Timestamp regDate;

//    @NotEmpty
//    @NotNull
//    private String isbn13;
    
    // 소장가능여부 : DB와는 상관없는 필드
    //private boolean hasBook;

    // 대출가능여부 : DB와는 상관없는 필드
    //private boolean isLoanAvailable;
}
