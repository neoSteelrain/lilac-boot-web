package com.steelrain.springboot.lilac.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Meta {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("pageable_count")
    private int pageableCount;
    @JsonProperty("is_end")
    private boolean isEnd;
}
