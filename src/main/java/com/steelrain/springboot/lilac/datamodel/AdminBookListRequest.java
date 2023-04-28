package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBookListRequest extends AdminSearchRequest{
    private ADMIN_BOOKLIST_TYPE blType;
}
