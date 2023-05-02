package com.sportsmania.swith.domain;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryFileVO {

    private Long file_no;
    private Long story_no;
    private String image_sub;

}
