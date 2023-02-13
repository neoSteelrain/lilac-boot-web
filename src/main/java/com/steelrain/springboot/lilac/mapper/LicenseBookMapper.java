package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LicenseBookMapper {

    void saveKakaoBookList(List<KaKaoBookDTO> kaKaoBookList);

    /*
    id	bigint	NO	PRI		auto_increment
    isbn13	bigint	NO	UNI
    title	varchar(1024)	YES	MUL
    contents	varchar(2048)	YES
    url	varchar(2048)	YES
    publish_date	datetime	YES
    authors	varchar(255)	YES
    publisher	varchar(255)	YES
    translators	varchar(255)	YES
    price	int	YES		0
    sale_price	int	YES		0
    thumbnail	varchar(255)	YES
    status	varchar(10)	YES
    reg_date	datetime	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED
    update_date	datetime	YES
     */
    @Select("SELECT id,isbn13,title,contents,url,publish_date,authors,publisher,translators,price,sale_price,thumbnail,status,reg_date,update_date FROM tbl_book WHERE isbn13=#{isbn}")
    KaKaoBookDTO findKakaoBookInfo(Long isbn);
}
