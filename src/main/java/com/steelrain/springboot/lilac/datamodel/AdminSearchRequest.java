package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSearchRequest {
    private int pageNum;
    private int[] licenseIds;
    private int[] subjectIds;
}
