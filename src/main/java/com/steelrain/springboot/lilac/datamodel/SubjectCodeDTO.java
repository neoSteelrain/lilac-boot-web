package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubjectCodeDTO {
    /*
    id	int	NO	PRI
    code	int	NO	UNI
    name	varchar(50)	NO	UNI
    key_word	varchar(255)	NO	UNI
    key_word_book	varchar(255)	NO
    reg_date	datetime	YES
    is_active	tinyint(1)	YES
    is_scheduled	tinyint(1)	YES
    page_token	varchar(50)	YES
    update_time	datetime	YES
     */

    private Integer id;
    private Integer code;
    private String name;
    private String keyWord;
    private String keyWordBook;
}
