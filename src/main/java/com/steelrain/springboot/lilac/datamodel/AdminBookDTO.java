package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBookDTO extends KaKaoBookDTO{
    private boolean isRecommend;
    private boolean isCandidate;
}
