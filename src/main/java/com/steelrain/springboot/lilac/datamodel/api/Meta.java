package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class Meta {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("pageable_count")
    private int pageableCount;
    @JsonProperty("is_end")
    private boolean isEnd;
}
