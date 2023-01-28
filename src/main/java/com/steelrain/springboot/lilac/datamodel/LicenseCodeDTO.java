package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LicenseCodeDTO {
    /*
    id	int	NO	PRI
    code	int	NO
    name	varchar(50)	NO	UNI
    key_word	varchar(50)	NO	UNI
    reg_date	datetime	YES
    schedule_json	json	YES
    is_active	tinyint(1)	YES
    is_scheduled	tinyint(1)	YES
    page_token	varchar(50)	YES
    update_time	datetime	YES
     */
    private Integer id;
    private Integer code;
    private String name;
    private String keyWord;
}
