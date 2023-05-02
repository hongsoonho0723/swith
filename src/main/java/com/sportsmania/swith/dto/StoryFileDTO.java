package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryFileDTO {

    private Long file_no;
    private Long story_no;
    private String image_sub;

}
