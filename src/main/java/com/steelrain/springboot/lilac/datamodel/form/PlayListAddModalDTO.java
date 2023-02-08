package com.steelrain.springboot.lilac.datamodel.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListAddModalDTO {
    private Long id;
    private String title;
    private Long youtubeId;
    private Long playListId;
}
