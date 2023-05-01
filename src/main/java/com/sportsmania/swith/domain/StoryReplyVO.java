package com.sportsmania.swith.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryReplyVO {

    private Long story_no;

    private String reply_writer;

    private String content;

    private LocalDateTime regdate;

}
