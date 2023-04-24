package com.steelrain.springboot.lilac.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PeriodDate {
    private String fromDate;
    private String toDate;
}
